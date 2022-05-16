package com.irostub.telegramtapbot.bot.command.none;

import com.irostub.telegramtapbot.bot.builder.MessageDirector;
import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class NonCommand implements Commandable {

    @Override
    public void execute(Update update, AbsSender absSender, String option) {
        SendMessage sendMessage = MessageDirector
                .createNonMessage(extractChatId(update), option);
        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("[NonCommand][execute] cannot execute, ex =", e);
        }
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.NONE;
    }
}
