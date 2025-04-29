package com.jobseeker.app.Configuration;

import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Model.Skill;
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
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setSkills(user.getSkills());
        return userDTO;
    }

    public static Skill skillFromStringSkill(String skill)
    {
        Skill skill1 = new Skill();
        skill1.setSkillName(skill);
        return skill1;
    }
}
