package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.bot.command.utils.ApiUtils;
import com.irostub.telegramtapbot.domain.Food;
import com.irostub.telegramtapbot.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateFoodService implements FoodService{
    private final FoodRepository repository;
    @Override
    public void run(Update update, AbsSender absSender, String options) {
        Food food = Food.createFood(options);
        repository.save(food);

        MessageEntity messageEntity = MessageEntity.builder()
                .type("text_mention")
                .offset(0)
                .length(ApiUtils.getTextUsername(update).length())
                .user(update.getMessage().getFrom())
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId().toString())
                .entities(List.of(messageEntity))
                .text(ApiUtils.getTextUsername(update) + "님\n "+options+"가(이) 등록되었습니다.")
                .build();

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FoodCommandOption getFoodCommandOption() {
        return FoodCommandOption.CREATE;
    }
}
