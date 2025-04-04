package com.jobseeker.app.Model;

import lombok.Data;

@Data
public class CompanyReview {
     private String workLifeBalance;
        private String review;
        private String workCulture;
        private String salary;
        private String workingHours;

   public String getWorkLifeBalance() {
      return workLifeBalance;
   }

   public void setWorkLifeBalance(String workLifeBalance) {
      this.workLifeBalance = workLifeBalance;
   }

   public String getReview() {
      return review;
   }

   public void setReview(String review) {
      this.review = review;
   }

   public String getWorkCulture() {
      return workCulture;
   }

   public void setWorkCulture(String workCulture) {
      this.workCulture = workCulture;
   }

   public String getSalary() {
      return salary;
   }

   public void setSalary(String salary) {
      this.salary = salary;
   }

   public String getWorkingHours() {
      return workingHours;
   }

   public void setWorkingHours(String workingHours) {
      this.workingHours = workingHours;
   }
}