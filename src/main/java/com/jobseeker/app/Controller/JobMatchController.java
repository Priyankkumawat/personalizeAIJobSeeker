package com.jobseeker.app.Controller;

import com.jobseeker.app.DTO.JobMatchRequest;
import com.jobseeker.app.DTO.JobMatchResponse;
import com.jobseeker.app.Service.OpenAIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobmatch")
public class JobMatchController {

    private final OpenAIService openAIService;

    public JobMatchController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping
    public ResponseEntity<List<JobMatchResponse>> matchJob(@RequestBody JobMatchRequest request) {
        try {
            List<JobMatchResponse> responses = openAIService.getLLMResponse(request);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
