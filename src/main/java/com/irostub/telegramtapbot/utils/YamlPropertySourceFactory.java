package com.irostub.telegramtapbot.utils;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class YamlPropertySourceFactory implements PropertySourceFactory {

    private static final String DEFAULT_PROFILE = "default";
    private static final String SPRING_PROFILES = "spring.profiles";
    private static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {

        Resource resource = encodedResource.getResource();
        List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load(resource.getFilename(), resource);

        String activeProfile = Optional.ofNullable(System.getProperty(SPRING_PROFILES_ACTIVE)).orElse(DEFAULT_PROFILE).trim();

        return propertySources.stream()
                .filter(source -> activeProfile.equals(String.valueOf(source.getProperty(SPRING_PROFILES))))
                .findFirst()
                .orElse(propertySources.get(0));
    }
}