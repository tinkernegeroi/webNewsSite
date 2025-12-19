package com.javaLab.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение для ошибок загрузки файлов (аватары, изображения новостей).
 * <p>
 * Возвращает HTTP статус 418 "I'm a teapot".
 * Используется в Mapper.processAvatar().
 * </p>
 */
@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class FileUploadException extends RuntimeException {

    /**
     * Создает исключение с сообщением об ошибке.
     *
     * @param message описание ошибки загрузки файла
     */
    public FileUploadException(String message) {
        super(message);
    }
}
