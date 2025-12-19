package com.javaLab.web.exceptions;

/**
 * Исключение выбрасывается когда новость не найдена по ID.
 * <p>
 * Используется в ModeratorService (getNewsById, editNews, deleteNewsById).
 * По умолчанию возвращает HTTP 404 NOT_FOUND.
 * </p>
 */
public class NewsNotFoundException extends RuntimeException {

    /**
     * Создает исключение с сообщением о не найденной новости.
     *
     * @param message описание ошибки (например, "News not found")
     */
    public NewsNotFoundException(String message) {
        super(message);
    }
}
