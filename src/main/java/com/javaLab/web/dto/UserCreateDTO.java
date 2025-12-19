package com.javaLab.web.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO для регистрации пользователя через AuthController.register().
 * Роль по умолчанию назначается USER.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    /**
     * Имя пользователя (уникальное).
     */
    private String username;

    /**
     * Пароль (будет зашифрован BCrypt).
     */
    private String password;

    /**
     * Email (уникальный).
     */
    private String email;

    /**
     * Аватар (опционально, multipart/form-data).
     */
    private MultipartFile avatar;
}
