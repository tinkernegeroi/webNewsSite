package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.*;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String avatar;

    private Role role;

    private int visitsCount;
}
