package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.domain.Food;
import com.irostub.telegramtapbot.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadFoodService implements FoodService{
    private final FoodRepository repository;

    public List<Food> getRandomFoodList(long limit) {
        return repository.findByRandom(limit);
    }

    @Override
    public FoodCommandOption getFoodCommandOption() {
        return FoodCommandOption.READ;
    }

    @Override
    public void run(Update update, AbsSender absSender, String pureCommand) {
        List<String> randomFoodList;

        if(pureCommand.isEmpty()){
             randomFoodList = getRandomFoodList(1).stream()
                     .map(Food::getName)
                     .collect(Collectors.toList());
        }else{
            randomFoodList = getRandomFoodList(Long.parseLong(pureCommand)).stream()
                    .map(Food::getName)
                    .collect(Collectors.toList());
        }

        MessageEntity messageEntity = MessageEntity.builder()
                .type("text_mention")
                .offset(0)
                .length(getTextUsername(update).length())
                .user(update.getMessage().getFrom())
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(update.getMessage().getChatId().toString())
                .entities(List.of(messageEntity))
                .text(getTextUsername(update) + "\n오늘의 밥은\n"+
                        String.join(", ", randomFoodList)+"!!")
                .build();

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("ex = ",e);
        }
    }

    private String getTextUsername(Update update) {
        return getFirstName(update) + getLastName(update);
    }

    private String getLastName(Update update) {
        String lastName = update.getMessage().getFrom().getLastName();
        if(StringUtils.hasText(lastName)){
            return lastName;
        }else{
            return "";
        }
    }

    private String getFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }
}
