package com.javaLab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO для ответа с данными новости.
 * Возвращается из ModeratorController.getNews() и getNewsById().
 * Реализует Serializable для возможной сериализации.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDTO implements Serializable {

    /**
     * ID новости.
     */
    private Long id;

    /**
     * Заголовок новости.
     */
    private String title;

    /**
     * Описание новости.
     */
    private String description;

    /**
     * Путь к изображению (/images/filename.jpg).
     */
    private String imageSrc;
}
