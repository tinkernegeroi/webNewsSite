package com.javaLab.web.models;

/**
 * Перечисление ролей пользователей системы.
 * <p>
 * Используется в Spring Security с префиксом ROLE_.
 * Хранится в БД как STRING через @Enumerated(EnumType.STRING).
 * </p>
 */
public enum Role {
    /**
     * Обычный пользователь (после регистрации).
     */
    VISITOR,

    /**
     * Модератор (управление новостями).
     */
    MODERATOR,

    /**
     * Администратор (полный доступ).
     */
    ADMIN
}
