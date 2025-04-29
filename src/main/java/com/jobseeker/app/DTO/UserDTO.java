package com.jobseeker.app.DTO;

import com.jobseeker.app.Model.Skill;

import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDTO
{
    private String name;

    private String email;

    private List<Skill> skillList = new ArrayList<>();

    private String token;

    private long expirationTime;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public List<Skill> getSkills()
    {
        return skillList;
    }

    public void setSkills(List<Skill> skills)
    {
        this.skillList = skills;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public long getExpirationTime()
    {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime)
    {
        this.expirationTime = expirationTime;
    }
}
