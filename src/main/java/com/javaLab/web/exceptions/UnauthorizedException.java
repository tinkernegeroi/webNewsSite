package com.javaLab.web.exceptions;

/**
 * Исключение выбрасывается при отсутствии авторизации.
 * <p>
 * Используется в UserService.getCurrentUsername() когда пользователь не авторизован.
 * По умолчанию возвращает HTTP 401 UNAUTHORIZED.
 * </p>
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Создает исключение с сообщением об отсутствии авторизации.
     *
     * @param message описание ошибки авторизации
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}
