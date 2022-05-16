package com.irostub.telegramtapbot.bot.validator;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PrefixValidator {
    private final AppProperties properties;

    @Deprecated
    public boolean validOuterPrefix(String message){
        return properties.getBot().getPrefix().stream()
                .parallel()
                .anyMatch(prefix -> message.strip().startsWith(prefix));
    }
}
