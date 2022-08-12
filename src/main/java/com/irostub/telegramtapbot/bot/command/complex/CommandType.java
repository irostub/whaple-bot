package com.irostub.telegramtapbot.bot.command.complex;

import java.util.Arrays;
import java.util.List;

public enum CommandType {
    NONE(""),
    RESTAURANT("밥", "ㅂ", "ㅇㅈㅁ", "오점뭐"),
    HELP( "도움말", "도움", "ㄷㅇ", "help", "h"),
    BIRTHDAY("생일", "ㅅㅇ", "birth"),
    CORONA("코로나", "ㅋㄹㄴ"),
    RPS("가위바위보", "ㄱㅂㅂ", "rps"),
    HANGANG("한강", "ㅎㄱ", "temp"),
    WEATHER("날씨", "ㄴㅆ", "weather"),
    GAME_DROP_BOX("드롭박스", "ㄷㄹㅂㅅ", "dropbox");

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
