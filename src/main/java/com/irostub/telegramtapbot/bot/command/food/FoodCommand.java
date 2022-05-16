package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * composite 패턴으로 변경가능 추후 수정
 */
@RequiredArgsConstructor
@Component
public class FoodCommand implements Commandable {
    private final FoodServiceHolder foodServiceHolder;

    @Override
    public void execute(Update update, AbsSender absSender, String pureCommand) {
        String option = separateInnerCommandPrefix(pureCommand);

        FoodCommandOption commandOption = FoodCommandOption.ofProperty(option);
        FoodService foodService = foodServiceHolder.get(commandOption);
        foodService.run(update, absSender, option);
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FOOD;
    }
}
