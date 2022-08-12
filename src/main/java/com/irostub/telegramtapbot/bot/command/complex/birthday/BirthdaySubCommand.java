package com.irostub.telegramtapbot.bot.command.complex.birthday;

import java.util.Arrays;
import java.util.List;

public enum BirthdaySubCommand {
    CREATE("등록", "ㄷㄹ", "추가", "ㅊㄱ"),
    READ("읽기", "내생일", "생일", "ㅇㄱ", "ㄴㅅㅇ", "ㅅㅇ"),
    UPDATE("업뎃", "업데이트", "갱신", "ㅇㄷ", "ㅇㄷㅇㅌ", "ㄱㅅ"),
    DELETE("삭제", "ㅅㅈ", "제거", "ㅈㄱ"),
    HELP("?", "도움말", "도움", "ㄷㅇ", "help", "h");

    private final List<String> properties;

    BirthdaySubCommand(String... properties) {
        this.properties = List.of(properties);
    }

    public static BirthdaySubCommand of(String property) {
        return Arrays.stream(BirthdaySubCommand.values())
                .parallel()
                .filter(s -> s.properties.contains(property))
                .findFirst()
                .orElse(HELP);
    }

    public List<String> getProperties() {
        return properties;
    }
}
