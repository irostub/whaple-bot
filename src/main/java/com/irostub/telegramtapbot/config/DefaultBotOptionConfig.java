package com.irostub.telegramtapbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class DefaultBotOptionConfig {
    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setProxyHost("192.168.0.157");
        defaultBotOptions.setProxyPort(54901);
        defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return defaultBotOptions;
    }
}
