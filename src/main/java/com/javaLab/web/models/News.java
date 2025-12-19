package com.javaLab.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA сущность для представления новости в базе данных.
 * <p>
 * Таблица: <code>news</code>
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {

    /**
     * Первичный ключ, автоинкремент.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Уникальный заголовок новости (не null).
     */
    @Column(unique = true, nullable = false)
    private String title;

    /**
     * Описание/текст новости (опционально).
     */
    @Column
    private String description;

    /**
     * Путь к изображению (/images/news_title_image.jpg).
     */
    @Column
    private String imageSrc;
}
