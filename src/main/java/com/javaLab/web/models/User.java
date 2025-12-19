package com.javaLab.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA сущность для представления пользователя в базе данных.
 * <p>
 * Таблица: <code>users</code>
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    /**
     * Первичный ключ, автоинкремент.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальное имя пользователя (логин, не null).
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Зашифрованный пароль BCrypt (не null).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Уникальный email (не null).
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Путь к аватару (/images/username_avatar.jpg) или значение по умолчанию.
     */
    @Column
    private String avatar;

    /**
     * Роль пользователя. Хранится как STRING в БД.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Счетчик посещений профиля (не null, по умолчанию 0).
     */
    @Column(nullable = false)
    private int visitsCount;
}
