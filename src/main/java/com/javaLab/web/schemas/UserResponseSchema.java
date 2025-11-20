package com.javaLab.web.schemas;

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
public class UserResponseSchema implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String avatar;

    private Role role;
}
