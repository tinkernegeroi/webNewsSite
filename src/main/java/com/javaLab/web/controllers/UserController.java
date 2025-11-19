package com.javaLab.web.controllers;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.models.UserModel;
import com.javaLab.web.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam MultipartFile avatar
    )
    {
        UserCreateDTO userCreateDTO = new UserCreateDTO(username, password, email, avatar);
        return userService.registerUser(userCreateDTO);
    }

}
