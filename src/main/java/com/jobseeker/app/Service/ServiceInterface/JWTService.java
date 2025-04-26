package com.jobseeker.app.Service.ServiceInterface;

public interface JWTService
{
    String generateToken(String username);

    boolean isTokenValid(String token, String username);

    String extractUsername(String token);

    public long getExpirationTime();
}
