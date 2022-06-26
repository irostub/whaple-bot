package com.irostub.telegramtapbot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Slf4j
@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            UserInfoHolder userInfoHolder = new UserInfoHolder();
            return Optional.of(userInfoHolder.getUser().getId().toString());
        };
    }
}
