package com.javaLab.web.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Конфигурационный класс, содержащий параметры для работы с изображениями.
 * <p>
 * Этот класс считывает значения путей и URL изображений из файла настроек
 * (например, application.properties или application.yml) с помощью аннотации {@link Value}.
 * Используется для централизованного хранения путей к файлам изображений и ссылок на них.
 * </p>
 */
@Component
@Data
public class ImageConfig {

    /**
     * Локальный путь на сервере, где хранятся изображения.
     */
    @Value("${image.path}")
    private String imagePath;

    /**
     * Базовый URL для обращений к изображениям через веб.
     */
    @Value("${image.url}")
    private String imageUrl;

    /**
     * Имя файла изображения, которое используется по умолчанию,
     * если у пользователя или объекта нет собственного изображения.
     */
    @Value("${image.default}")
    private String defaultImage;
}
