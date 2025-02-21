package com.mavericksstube.maverickshub.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudConfig {
    @Value("${cloud.api.name}")
    private String cloudName;
    @Value("${cloud.api.key}")
    private String apiKey;
    @Value("${cloud.api.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<?, ?> map = ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
           );

           return new Cloudinary(map);
    }
}
