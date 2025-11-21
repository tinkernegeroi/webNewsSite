package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
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
