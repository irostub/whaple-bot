package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.bot.command.CommandNotFoundException;
import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.OptionNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum FoodCommandOption {
    CREATE("등록", "추가", "add", "create", "new", "register", "a", "c", "ㄷㄹ", "ㅊㄱ"),
    DELETE("삭제", "제거", "remove", "delete", "d", "r", "ㅅㅈ", "ㅈㄱ"),
    UPDATE("업뎃", "수정", "update", "u", "ㅇㄷ", "ㅅㅈ"),
    READ();

    private final List<String> properties;

    FoodCommandOption(String... s) {
        this.properties = List.of(s);
    }

    public static FoodCommandOption ofProperty(String option) {
        //option 없는 경우 read limit 1
        if (option.isEmpty()) {
            return FoodCommandOption.READ;
        }

        try {
            //option 이 수로 변환 가능한 경우 read limit long
            Long.parseLong(option);
            return FoodCommandOption.READ;
        } catch (NumberFormatException e) {

            //그 외의 경우 매치되면 해당 타입 반환, 매치 안되면 exception 발생
            return Arrays.stream(FoodCommandOption.values())
                    .filter(o -> o.properties.contains(option))
                    .findFirst()
                    .orElseThrow(() -> new OptionNotFoundException(option));
        }
    }
}
