package com.jobseeker.app.Filter;

import com.jobseeker.app.Service.ServiceInterface.JWTService;

import java.io.IOException;

import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component()
public class JWTAuthenticationFilter extends OncePerRequestFilter
{
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(
            HandlerExceptionResolver handlerExceptionResolver,
            JWTService jwtService,
            UserDetailsService userDetailsService)
    {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");
        System.out.println("JWTAuthenticationFilter autheader: " + authHeader);

        if( authHeader != null && authHeader.startsWith("Bearer "))
        {
            System.out.println("JWTAuthenticationFilter authHeader not null: " + authHeader);
            try
            {
                String jwtToken = authHeader.substring(7);
                System.out.println("JWTAuthenticationFilter jwtToken : " + jwtToken);
                String userEmail = jwtService.extractUsername(jwtToken);
                System.out.println("JWTAuthenticationFilter userEmail : " + userEmail);


                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



                if(userEmail != null && authentication == null)
                {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                    System.out.println("JWTAuthenticationFilter username: " + userDetails.getUsername());

                    if(jwtService.isTokenValid(jwtToken, userEmail))
                    {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }

                filterChain.doFilter(request, response);
            } catch (Exception e)
            {
                handlerExceptionResolver.resolveException(request, response, null, e);
            }

        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
