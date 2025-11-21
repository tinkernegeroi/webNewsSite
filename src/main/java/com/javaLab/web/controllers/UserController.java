package com.javaLab.web.controllers;

import com.javaLab.web.dto.ServerTimeDTO;
import com.javaLab.web.dto.UserResponseDTO;
import com.javaLab.web.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> profile(HttpSession session) {
        return userService.getProfile(session);
    }

    @GetMapping("/time")
    public ResponseEntity<ServerTimeDTO> time(){
        return ResponseEntity.ok(userService.getServerTime());
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(HttpSession session,
                                          @RequestParam MultipartFile file) {
        return userService.uploadAvatar(session, file);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar(HttpSession session) {
        return userService.deleteAvatar(session);
    }
}
