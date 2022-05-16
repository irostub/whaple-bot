package com.irostub.telegramtapbot.bot.command;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.validation.constraints.NotNull;

public interface Commandable {
    void execute(Update update, @NotNull AbsSender absSender, String option);

    CommandType getCommandType();

    default String extractChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    default String separateInnerCommandPrefix(String pureCommand) {
        if (pureCommand.contains(" ")) {
            return StringUtils.substringAfter(pureCommand, " ").strip();
        }
        return "";
    }
}
