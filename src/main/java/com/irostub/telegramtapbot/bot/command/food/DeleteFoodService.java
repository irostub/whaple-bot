package com.irostub.telegramtapbot.bot.command.food;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service
public class DeleteFoodService implements FoodService{
    @Override
    public void run(Update update, AbsSender absSender, String options) {

    }

    @Override
    public FoodCommandOption getFoodCommandOption() {
        return null;
    }
}
