package com.javaLab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO для создания/редактирования новости.
 * Используется в ModeratorController (multipart/form-data).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCreateDTO {

    /**
     * Заголовок новости.
     */
    private String title;

    /**
     * Описание/текст новости.
     */
    private String description;

    /**
     * Изображение для новости (multipart/form-data).
     */
    private MultipartFile image;
}
