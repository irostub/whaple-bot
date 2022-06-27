package com.irostub.telegramtapbot.bot.command.utils;

public class ParseUtils {
    public static boolean canParseInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
