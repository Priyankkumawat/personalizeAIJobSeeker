package com.jobseeker.app.Configuration;

import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConversion
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public  static User userFromUserDTO(UserDTO userDTO)
    {
        return objectMapper.convertValue(userDTO, User.class);
    }

    public static UserDTO userDTOFromUser(User user)
    {
        return objectMapper.convertValue(user, UserDTO.class);
    }
}
