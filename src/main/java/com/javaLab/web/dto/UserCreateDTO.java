package com.javaLab.web.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private String username;

    private String password;

    private String email;

    private MultipartFile avatar;
}
