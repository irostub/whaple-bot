package com.irostub.telegramtapbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class DefaultBotOptionConfig {
    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        defaultBotOptions.setProxyHost("whaple.iro.ooo");
        defaultBotOptions.setProxyPort(50204);
        defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return defaultBotOptions;
    }
}
