package com.javaLab.web.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    private String username;

    private String password;
}
