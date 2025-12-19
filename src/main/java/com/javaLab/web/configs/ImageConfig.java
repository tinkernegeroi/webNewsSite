package com.javaLab.web.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ImageConfig {

    @Value("${image.path}")
    private String imagePath;

    @Value("${image.url}")
    private String imageUrl;

    @Value("${image.default}")
    private String defaultImage;

}
