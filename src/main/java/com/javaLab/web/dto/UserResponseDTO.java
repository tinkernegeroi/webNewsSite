package com.javaLab.web.dto;

import com.javaLab.web.models.Role;
import lombok.*;

import java.io.Serializable;

/**
 * DTO для ответа с данными профиля пользователя.
 * Возвращается из UserController.profile().
 * Реализует Serializable для возможной сериализации.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO implements Serializable {

    /**
     * ID пользователя.
     */
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Email пользователя.
     */
    private String email;

    /**
     * Путь к аватару (/images/avatar.jpg) или null.
     */
    private String avatar;

    /**
     * Роль пользователя.
     */
    private Role role;

    /**
     * Количество посещений профиля.
     */
    private int visitsCount;
}
