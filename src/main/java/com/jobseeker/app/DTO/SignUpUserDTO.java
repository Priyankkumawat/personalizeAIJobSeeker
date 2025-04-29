package com.jobseeker.app.DTO;

import com.jobseeker.app.Model.Skill;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignUpUserDTO
{
    @Email
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    private String password;

    private List<String> skillList;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<String> getSkills()
    {
        return skillList;
    }

    public void setSkills(List<String> skills)
    {
        this.skillList = skills;
    }
}
