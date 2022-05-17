package com.irostub.telegramtapbot.bot.command.none;

import com.irostub.telegramtapbot.bot.builder.MessageDirector;
import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Component
public class NonCommand implements Commandable {

    @Override
    public void execute(Update update, AbsSender absSender, List<String> options) {
        SendMessage sendMessage = MessageDirector
                .createNonMessage(extractChatId(update), options.get(0));
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

    @Override
    public List<String> separateOptions(String options) {
        return null;
    }
}
