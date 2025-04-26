package com.jobseeker.app.Service;

import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Repository.UserRepository;
import com.jobseeker.app.Service.ServiceInterface.UserService;

import java.util.Optional;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO)
    {
       return null;
    }

    @Override
    public Optional<UserDTO> getUserByUserName(String email)
    {
//        return Optional.ofNullable(userRepository.findByEmail(email))
//                .map(UserDTO::userDTOFromUser);
        return Optional.empty();
    }
}
