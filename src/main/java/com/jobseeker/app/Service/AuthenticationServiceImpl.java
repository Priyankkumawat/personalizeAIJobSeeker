package com.jobseeker.app.Service;

import com.jobseeker.app.Configuration.ObjectConversion;
import com.jobseeker.app.DTO.LoginUserDTO;
import com.jobseeker.app.DTO.SignUpUserDTO;
import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Model.User;
import com.jobseeker.app.Repository.UserRepository;
import com.jobseeker.app.Service.ServiceInterface.AuthenticationService;
import com.jobseeker.app.Service.ServiceInterface.JWTService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JWTService jwtService)
    {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO signUp(SignUpUserDTO signUpUserDTO)
    {
        System.out.println("signUp serviceImpl : " + signUpUserDTO.getEmail());
        User user = new User();
        user.setEmail(signUpUserDTO.getEmail());
        user.setName(signUpUserDTO.getName());
        user.setSkills(signUpUserDTO.getSkills());
        user.setPassword(passwordEncoder.encode(signUpUserDTO.getPassword()));

        System.out.println("signUp serviceImpl : " + user.getEmail());
        User savedUser = userRepository.save(user);
        return setToken(ObjectConversion.userDTOFromUser(savedUser));
    }

    @Override
    public UserDTO authenticate(LoginUserDTO loginUserDTO)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.getEmail(),
                        loginUserDTO.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginUserDTO.getEmail()).orElseThrow();
        return setToken(ObjectConversion.userDTOFromUser(user));
    }

    private UserDTO setToken(UserDTO userDTO)
    {
        String jwtToken = jwtService.generateToken(userDTO.getEmail());

        userDTO.setToken(jwtToken);

        userDTO.setExpirationTime(jwtService.getExpirationTime());

        return userDTO;
    }

}
