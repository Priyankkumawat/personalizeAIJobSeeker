package com.jobseeker.app.Service;

import com.jobseeker.app.Configuration.ObjectConversion;
import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Model.User;
import com.jobseeker.app.Repository.UserRepository;
import com.jobseeker.app.Service.ServiceInterface.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email)
    {
        return userRepository.findByEmail(email)
                .map(ObjectConversion::userDTOFromUser);
    }

    @Override
    public Optional<List<UserDTO>> getAllUser()
    {
        return Optional.of(userRepository.findAll()
                .stream()
                .map(ObjectConversion::userDTOFromUser)
                .toList());
    }

    @Override
    public Optional<String> deleteUserByEmail(String email)
    {
        userRepository.findByEmail(email).ifPresent(userRepository::delete);
        return Optional.of("Success");
    }

    @Override
    public Optional<UserDTO> getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("getCurrentUser authentication: " + authentication);
        User user = (User) authentication.getPrincipal();
        user.getSkills().size();
        System.out.println("getCurrentUser user: " + user.getEmail());
        System.out.println("getCurrentUser user: " + user.getName());
        System.out.println("getCurrentUser user: " + user.getSkills());
        System.out.println("getCurrentUser user: " + user.getPassword());
        System.out.println("getCurrentUser user: " + user.getId());
        System.out.println("getCurrentUser user: " + user.getRole());

        return Optional.of(ObjectConversion.userDTOFromUser(user));
    }
}
