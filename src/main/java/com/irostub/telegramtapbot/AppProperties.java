package com.irostub.telegramtapbot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter(value= AccessLevel.PACKAGE)
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Bot bot;

    @Getter @Setter
    public static class Bot{
        private String token;
        private String botUsername;
        private Set<String> prefix = new HashSet<>();
    }
}
