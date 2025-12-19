package com.javaLab.web.dto;

import lombok.*;

/**
 * DTO для входа в систему.
 * Используется в AuthController.login() (JSON).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    /**
     * Имя пользователя или email.
     */
    private String username;

    /**
     * Пароль.
     */
    private String password;
}
