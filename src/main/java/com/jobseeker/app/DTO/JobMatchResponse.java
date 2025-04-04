package com.jobseeker.app.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class JobMatchResponse {
    private String jobTitle;
    private double skillMatchPercent;
    private double companyReliabilityScore;
    private List<String> upskillSuggestions;
    private String feedback;
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSkillMatchPercent() {
        return skillMatchPercent;
    }

    public void setSkillMatchPercent(double skillMatchPercent) {
        this.skillMatchPercent = skillMatchPercent;
    }

    public double getCompanyReliabilityScore() {
        return companyReliabilityScore;
    }

    public void setCompanyReliabilityScore(double companyReliabilityScore) {
        this.companyReliabilityScore = companyReliabilityScore;
    }

    public List<String> getUpskillSuggestions() {
        return upskillSuggestions;
    }

    public void setUpskillSuggestions(List<String> upskillSuggestions) {
        this.upskillSuggestions = upskillSuggestions;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}