package com.javaLab.web.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ImageConfig {

    @Value("${image.path}")
    private String imagePath;

    @Value("${image.url}")
    private String imageUrl;

    @Value("${image.default}")
    private String defaultImage;

}
