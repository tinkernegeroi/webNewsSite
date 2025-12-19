package com.javaLab.web.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация Web MVC для обработки статических ресурсов.
 * Регистрирует обработчик для файлов изображений из ImageConfig.
 */
@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final ImageConfig imageConfig;

    /**
     * Регистрирует статические ресурсы /images/**.
     * Сопоставляет URL с локальной файловой системой.
     * Отключает кэширование (cachePeriod=0).
     *
     * @param registry реестр обработчиков ресурсов
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:"+ imageConfig.getImagePath())
                .setCachePeriod(0);
    }
}
