package com.javaLab.web.controllers;

import com.javaLab.web.dto.UserCreateDTO;
import com.javaLab.web.dto.UserLoginDTO;
import com.javaLab.web.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam(required = false) MultipartFile avatar
    ){
        UserCreateDTO dto = new UserCreateDTO(username, password, email, avatar);
        return authService.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserLoginDTO userLoginDTO,
            HttpSession session
    ){
        return authService.login(userLoginDTO, session);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session){
        return authService.logout(session);
    }
}
