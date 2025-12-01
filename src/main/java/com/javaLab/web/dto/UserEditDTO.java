package com.javaLab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserEditDTO {

    private String username;

    private String password;

    private String email;

    private MultipartFile avatar;
}
