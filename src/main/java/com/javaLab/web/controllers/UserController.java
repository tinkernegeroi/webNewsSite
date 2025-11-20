package com.javaLab.web.controllers;

import com.javaLab.web.schemas.UserCreateSchema;
import com.javaLab.web.schemas.UserLoginSchema;
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
        UserCreateSchema userCreateSchema = new UserCreateSchema(username, password, email, avatar);
        return userService.registerUser(userCreateSchema);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String username,
            @RequestParam String password
    ){
        UserLoginSchema userLoginSchema = new UserLoginSchema(username, password);
        return userService.loginUser(userLoginSchema);
    }

}
