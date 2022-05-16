package com.irostub.telegramtapbot.bot.command.router;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class RouteObjectDto {
    private Update update;

    @NotNull
    private AbsSender absSender;

    @NotNull
    private String pureCommand;
}
