package com.irostub.telegramtapbot.bot.command.utils;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ExtractUtils {
    public static String getChatId(CommandGatewayPack pack) {
        return pack.getUpdate().getMessage().getChatId().toString();
    }

    public static String extractUsername(Update update) {
        return getLastName(update) + getFirstName(update);
    }

    private static String getLastName(Update update) {
        String lastName = update.getMessage().getFrom().getLastName();
        if (org.springframework.util.StringUtils.hasText(lastName)) {
            return lastName;
        } else {
            return "";
        }
    }

    private static String getFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }
}
