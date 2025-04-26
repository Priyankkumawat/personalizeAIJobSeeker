package com.jobseeker.app.Service.ServiceInterface;

import com.jobseeker.app.DTO.UserDTO;

import java.util.Optional;

public interface UserService
{
    public UserDTO createUser(UserDTO userDTO);

    public Optional<UserDTO> getUserByUserName(String userName);
}
