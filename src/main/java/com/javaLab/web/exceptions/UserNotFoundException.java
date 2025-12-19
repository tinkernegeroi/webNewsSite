package com.javaLab.web.exceptions;

/**
 * Исключение выбрасывается когда пользователь не найден по ID/username.
 * <p>
 * Используется в AdminService, AuthService, UserService.
 * По умолчанию возвращает HTTP 404 NOT_FOUND.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Создает исключение с сообщением о не найденном пользователе.
     *
     * @param message описание ошибки (например, "User not found")
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
