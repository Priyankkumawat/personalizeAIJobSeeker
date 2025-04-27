package com.jobseeker.app.Service.ServiceInterface;

import com.jobseeker.app.DTO.LoginUserDTO;
import com.jobseeker.app.DTO.SignUpUserDTO;
import com.jobseeker.app.DTO.UserDTO;

public interface AuthenticationService
{
    public UserDTO signUp(SignUpUserDTO signUpUserDTO);

    public UserDTO authenticate(LoginUserDTO loginUserDTO);
}
