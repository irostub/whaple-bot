package com.irostub.telegramtapbot.bot.command.complex;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class CommandGatewayPack {
    private String options;
    private Update update;
    private AbsSender absSender;

    public CommandGatewayPack(String options, Update update, AbsSender absSender) {
        this.options = options;
        this.update = update;
        this.absSender = absSender;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public AbsSender getAbsSender() {
        return absSender;
    }

    public void setAbsSender(AbsSender absSender) {
        this.absSender = absSender;
    }
}
