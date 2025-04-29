package com.jobseeker.app.Service;

import com.jobseeker.app.Configuration.ObjectConversion;
import com.jobseeker.app.DTO.LoginUserDTO;
import com.jobseeker.app.DTO.SignUpUserDTO;
import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Model.Skill;
import com.jobseeker.app.Model.User;
import com.jobseeker.app.Repository.UserRepository;
import com.jobseeker.app.Service.ServiceInterface.AuthenticationService;
import com.jobseeker.app.Service.ServiceInterface.JWTService;

import java.util.List;

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
        User user = new User();
        user.setEmail(signUpUserDTO.getEmail());
        user.setName(signUpUserDTO.getName());
        user.setSkills(setSKill(signUpUserDTO.getSkills(), user));
        user.setPassword(passwordEncoder.encode(signUpUserDTO.getPassword()));

        User savedUser = userRepository.save(user);
        System.out.println("signUp serviceImpl : " + savedUser.getEmail());
        return setToken(ObjectConversion.userDTOFromUser(savedUser));
    }

    private List<Skill> setSKill(List<String> skillList, User user)
    {
        return skillList.stream()
                .map(skillName -> {
                    Skill skill = ObjectConversion.skillFromStringSkill(skillName);
                    skill.setUser(user);
                    return skill;
                }).toList();
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
        System.out.println("authenticate serviceImpl : " + loginUserDTO.getEmail());
        User user = userRepository.findByEmail(loginUserDTO.getEmail()).orElseThrow();
        return setToken(ObjectConversion.userDTOFromUser(user));
    }

    private UserDTO setToken(UserDTO userDTO)
    {
        String jwtToken = jwtService.generateToken(userDTO.getEmail());

        System.out.println("AuthenticationServiceImpl jwtToken: " + jwtToken);

        userDTO.setToken(jwtToken);

        userDTO.setExpirationTime(jwtService.getExpirationTime());

        return userDTO;
    }

}
