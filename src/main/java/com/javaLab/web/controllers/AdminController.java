package com.javaLab.web.controllers;


import com.javaLab.web.dto.AdminEditUserDTO;
import com.javaLab.web.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PatchMapping(value = "/{username}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateUserAsAdmin(
            @PathVariable String username,
            @ModelAttribute AdminEditUserDTO dto
    ) {
        return adminService.updateUser(username, dto);
    }
}
