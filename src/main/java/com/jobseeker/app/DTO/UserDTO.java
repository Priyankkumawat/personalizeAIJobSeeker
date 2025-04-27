package com.jobseeker.app.DTO;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class UserDTO
{
    private String name;

    private String email;

    private String password;

    private List<String> skills;

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
        return skills;
    }

    public void setSkills(List<String> skills)
    {
        this.skills = skills;
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
