package com.irostub.telegramtapbot.bot.command;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Commandable {
    default void run(Update update, @NotNull AbsSender absSender, String optionString) {
        List<String> options = separateOptions(optionString);
        execute(update, absSender, options);
    }

    void execute(Update update, @NotNull AbsSender absSender, List<String> optionList);

    CommandType getCommandType();

    default String extractChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    List<String> separateOptions(String options);
}
