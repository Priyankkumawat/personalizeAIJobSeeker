package com.jobseeker.app.Model;

import java.util.List;

public class JobData {
    private String jobId;
    private String jobTitle;
    private String jobPostDate;
    private String description;
    private List<String> skillSet;
    private String experience;
    private String companyName;
    private String jobLocation;
    private CompanyReview companyReview;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPostDate() {
        return jobPostDate;
    }

    public void setJobPostDate(String jobPostDate) {
        this.jobPostDate = jobPostDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public CompanyReview getCompanyReview() {
        return companyReview;
    }

    public void setCompanyReview(CompanyReview companyReview) {
        this.companyReview = companyReview;
    }

}

