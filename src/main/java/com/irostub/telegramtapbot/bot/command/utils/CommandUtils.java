package com.irostub.telegramtapbot.bot.command.utils;

import com.irostub.telegramtapbot.bot.command.CommandType;

public class CommandUtils {
    public static CommandType parseCommandType(String messageWithoutPrefix){
        String parsedCommandString = messageWithoutPrefix;
        if(messageWithoutPrefix.contains(" ")){
            parsedCommandString = messageWithoutPrefix.substring(0, messageWithoutPrefix.indexOf(" "));
        }
        return CommandType.ofProperty(parsedCommandString);
    }
}
