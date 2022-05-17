package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

/**
 * composite 패턴으로 변경가능 추후 수정
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FoodCommand implements Commandable {
    private final FoodServiceHolder foodServiceHolder;

    @Override
    public void execute(Update update, AbsSender absSender, List<String> options) {
        //
        //1
        //등록 부대찌개
        FoodCommandOption commandOption;
        if (options.size() == 0) {
            options.add("");
            commandOption = FoodCommandOption.READ;
        } else if(options.size() == 1){
            commandOption = FoodCommandOption.READ;
        } else if(options.size() == 2){
            commandOption = FoodCommandOption.ofProperty(options.get(0));
        }else{
            return;
        }

        FoodService foodService = foodServiceHolder.get(commandOption);
        foodService.run(update, absSender, commandOption.equals(FoodCommandOption.READ)?options.get(0):options.get(1));
    }

    @Override
    public List<String> separateOptions(String options) {
        return List.of(StringUtils.split(options," ",2));
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.FOOD;
    }
}
