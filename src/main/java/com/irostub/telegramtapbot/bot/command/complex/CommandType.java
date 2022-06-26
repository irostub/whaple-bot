package com.irostub.telegramtapbot.bot.command.complex;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    NONE(""),
    RESTAURANT("밥", "ㅂ", "ㅇㅈㅁ", "오점뭐");

    private final List<String> properties;

    CommandType(String ...properties) {
        this.properties = List.of(properties);
    }

    public static CommandType of(String command) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.properties.contains(command))
                .findFirst()
                .orElse(NONE);
    }

    public static boolean contains(String command) {
        return Arrays.stream(CommandType.values())
                .anyMatch(t -> t.properties.contains(command));
    }
}
