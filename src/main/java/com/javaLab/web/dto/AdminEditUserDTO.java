package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminEditUserDTO {
    private String username;
    private String email;
    private MultipartFile avatar;
    private Role role;
}
