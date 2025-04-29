package com.jobseeker.app.Service.ServiceInterface;

import com.jobseeker.app.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    public Optional<UserDTO> getUserByEmail(String email);

    public Optional<List<UserDTO>> getAllUser();

    public Optional<String> deleteUserByEmail(String email);

    public Optional<UserDTO> getCurrentUser();
}
