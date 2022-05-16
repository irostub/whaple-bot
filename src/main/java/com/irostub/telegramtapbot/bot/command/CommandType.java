package com.irostub.telegramtapbot.bot.command;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    NONE(""),
    FOOD("밥");

    private final List<String> properties;

    CommandType(String...s){
        this.properties = Arrays.asList(s);
    }

    public static CommandType ofProperty(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.properties.contains(command))
                .findFirst()
                .orElse(NONE);
    }

    public boolean containString(String s) {
        return properties.contains(s);
    }

}
