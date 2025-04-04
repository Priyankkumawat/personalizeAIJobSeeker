package com.jobseeker.app.Loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobseeker.app.Model.JobData;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class JobDataLoader {

    private List<JobData> jobList;

    @PostConstruct
    public void loadJobs() throws IOException {
        System.out.println("Loading jobs.json...");

        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("jobs.json");

        InputStream inputStream = resource.getInputStream();
        jobList = mapper.readValue(inputStream, new TypeReference<>() {});

        System.out.println("Loaded " + jobList.size() + " jobs from JSON.");
    }

    public List<JobData> getAllJobs() {
        return jobList;
    }
}

