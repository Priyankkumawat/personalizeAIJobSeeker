package com.jobseeker.app.Controller;

import com.jobseeker.app.DTO.LoginUserDTO;
import com.jobseeker.app.DTO.SignUpUserDTO;
import com.jobseeker.app.DTO.UserDTO;
import com.jobseeker.app.Service.AuthenticationServiceImpl;
import com.jobseeker.app.Service.ServiceInterface.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController
{
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpUserDTO signUpUserDTO)
    {
        System.out.println("POST: signup  - SignUpUserDTO: " + signUpUserDTO);
        UserDTO userDTO = authenticationService.signUp(signUpUserDTO);
        System.out.println("POST: signup  - UserDTO: " + userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginUserDTO loginUserDTO)
    {
        System.out.println("POST: login  - LoginUserDTO: " + loginUserDTO);
        UserDTO userDTO = authenticationService.authenticate(loginUserDTO);

        return ResponseEntity.ok(userDTO);
    }
}
