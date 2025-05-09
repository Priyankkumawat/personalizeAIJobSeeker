package com.jobseeker.app.Service;

import com.jobseeker.app.Service.ServiceInterface.JWTService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService
{
    @Value("${spring.jwt.expiration-time}")
    private static long expirationTime;

    @Value("${spring.jwt.secret-key}")
    private static String secretKey;

    @Override
    public String generateToken(String username)
    {
        return buildToken(new HashMap<>(), username);
    }

    @Override
    public boolean isTokenValid(String token, String username)
    {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }

    @Override
    public String extractUsername(String token)
    {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public long getExpirationTime()
    {
        return expirationTime;
    }

    private String buildToken(Map<String, Object> extraClaims, String userName)
    {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey())
                .compact();
    }

    private Boolean isTokenExpired(String token)
    {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
