package com.irostub.telegramtapbot.bot.command.food;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface FoodService {
    void run(Update update, AbsSender absSender, String pureCommand);

    FoodCommandOption getFoodCommandOption();
//    {
//        sendMessage(chatId, absSender);
//    }

//    void sendMessage(String chatId, AbsSender absSender);
}
