package com.jobseeker.app.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jobseeker.app.DTO.JobMatchRequest;
import com.jobseeker.app.DTO.JobMatchResponse;
import com.jobseeker.app.Loader.JobDataLoader;
import com.jobseeker.app.Model.JobData;
import com.jobseeker.app.Configuration.OpenAIConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {

    private OpenAIConfig config;

    private JobDataLoader jobDataLoader;

    public OpenAIService(OpenAIConfig config, JobDataLoader jobDataLoader) {
        this.config = config;
        this.jobDataLoader = jobDataLoader;
    }

    public List<JobMatchResponse> getLLMResponse(JobMatchRequest request) throws IOException, InterruptedException {
        List<JobData> allJobs = jobDataLoader.getAllJobs();

        System.out.println(request.getJobTitle());

        // Filter jobs roughly based on title, experience, skills
        List<JobData> matchingJobs = allJobs.stream()
                .filter(job -> isRelevant(job, request))
                .limit(5) // avoid overloading LLM; you can paginate or expand later
                .toList();

        List<JobMatchResponse> responses = new ArrayList<>();
        for (JobData job : matchingJobs) {
            String prompt = generatePrompt(request, job);
            String apiResponse = callOpenAI(prompt);
            responses.add(parseLLMResponse(apiResponse, job.getJobTitle(), job.getCompanyName()));
        }

        return responses;
    }

    private boolean isRelevant(JobData job, JobMatchRequest req) {
        boolean titleMatch = job.getJobTitle().toLowerCase().contains(req.getJobTitle().toLowerCase());
        boolean skillOverlap = job.getSkillSet().stream().anyMatch(s -> req.getSkills().contains(s));
        boolean expOkay = job.getExperience().contains(String.valueOf(req.getExperienceYears()));
        return titleMatch && skillOverlap && expOkay;
    }

    private String generatePrompt(JobMatchRequest req, JobData job) {
        ObjectMapper mapper = new ObjectMapper();
        String jobJson;
        try {
            jobJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(job);
        } catch (JsonProcessingException e) {
            jobJson = "ERROR converting job";
        }

        return String.format("""
                You are a job-matching assistant. Based on the job posting and user profile below, evaluate the match and return a JSON response.
                
                User Profile:
                - Job Title: %s
                - Location: %s
                - Skills: %s
                - Experience: %d years
                
                Job Posting (JSON):
                %s
                
                Respond ONLY in the following JSON format:
                
                {
                  "skillMatchPercent": <int>,            // from 0 to 100
                  "companyReliabilityScore": <int>,      // from 0 to 100
                  "upskillSuggestions": [<string>, ...], // list of skills
                  "feedback": "<string>"                 // recommendation summary
                }
                
                Example:
                {
                  "skillMatchPercent": 95,
                  "companyReliabilityScore": 88,
                  "upskillSuggestions": ["RxJS", "Unit Testing in Angular"],
                  "feedback": "You are a strong match for this role. Applying now is recommended due to skill alignment and positive company culture."
                }
                """,
                req.getJobTitle(),
                req.getLocation(),
                String.join(", ", req.getSkills()),
                req.getExperienceYears(),
                jobJson
        );
    }

    private String callOpenAI(String prompt) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        root.put("model", "gpt-3.5-turbo"); // from config
        root.put("temperature", 0.7);

        ArrayNode messages = mapper.createArrayNode();
        ObjectNode message = mapper.createObjectNode();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);
        root.set("messages", messages);

        String requestJson = mapper.writeValueAsString(root);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl()))
                .header("Authorization", "Bearer " + config.getApiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestJson))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("🔁 Raw OpenAI response:\n" + response.body());

        return response.body();
    }

    private JobMatchResponse parseLLMResponse(String raw, String jobTitile, String companyName) throws JsonProcessingException {
        // Extract structured reply from OpenAI's response
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(raw);

        // Defensive check
        JsonNode choices = root.path("choices");
        if (choices == null || !choices.isArray() || choices.size() == 0) {
            System.err.println("⚠️ OpenAI response is missing 'choices':\n" + raw);
            return fallbackResponse("LLM did not return a valid response.");
        }

        JsonNode message = choices.get(0).path("message");
        JsonNode contentNode = message.path("content");

        ObjectMapper mapperRes = new ObjectMapper();

// `content` is a JSON string returned inside OpenAI's response
        String content = contentNode.isMissingNode() ? "[No content in LLM response]" : contentNode.asText();

// ✅ Parse the string content (which is itself a JSON object)
        JsonNode parsedJson = mapperRes.readTree(content);

        int skillMatch = parsedJson.path("skillMatchPercent").asInt();
        int companyScore = parsedJson.path("companyReliabilityScore").asInt();
        List<String> suggestions = mapper.convertValue(
                parsedJson.path("upskillSuggestions"), new TypeReference<List<String>>() {}
        );
        String feedback = parsedJson.path("feedback").asText();

// Set into JobMatchResponse
        JobMatchResponse res = new JobMatchResponse();
        res.setSkillMatchPercent(skillMatch);
        res.setCompanyReliabilityScore(companyScore);
        res.setUpskillSuggestions(suggestions);
        res.setFeedback(feedback); // or parse actual feedback from content
        res.setJobTitle(jobTitile);
        res.setCompanyName(companyName);
        return res;
    }

    private JobMatchResponse fallbackResponse(String message) {
        JobMatchResponse fallback = new JobMatchResponse();
        fallback.setSkillMatchPercent(0);
        fallback.setCompanyReliabilityScore(0);
        fallback.setUpskillSuggestions(List.of());
        fallback.setFeedback(message);
        return fallback;
    }
}
