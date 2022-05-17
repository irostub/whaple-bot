package com.irostub.telegramtapbot.bot.command.utils;

import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ApiUtils {
    public static String getTextUsername(Update update) {
        return getFirstName(update) + getLastName(update);
    }

    private static String getLastName(Update update) {
        String lastName = update.getMessage().getFrom().getLastName();
        if(StringUtils.hasText(lastName)){
            return lastName;
        }else{
            return "";
        }
    }

    private static String getFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }
}
