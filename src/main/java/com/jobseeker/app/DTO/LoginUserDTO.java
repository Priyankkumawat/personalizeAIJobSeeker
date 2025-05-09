package com.jobseeker.app.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class LoginUserDTO
{
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters")
    private String password;

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
}
