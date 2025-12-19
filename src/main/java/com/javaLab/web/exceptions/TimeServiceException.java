package com.javaLab.web.exceptions;

/**
 * Исключение для ошибок работы с серверным временем.
 * <p>
 * Используется в UserService.getServerTime() при ошибках форматирования.
 * Содержит причину (cause) для детального анализа.
 * </p>
 */
public class TimeServiceException extends RuntimeException {

    /**
     * Создает исключение с сообщением и причиной ошибки.
     *
     * @param message описание ошибки времени
     * @param cause исходное исключение (например, DateTimeException)
     */
    public TimeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
