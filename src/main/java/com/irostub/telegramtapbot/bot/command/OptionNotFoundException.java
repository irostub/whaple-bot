package com.irostub.telegramtapbot.bot.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OptionNotFoundException extends NotFoundException{
    public OptionNotFoundException() {
        super();
    }

    public OptionNotFoundException(String message) {
        super(message);
        log.warn("option not found, option = {}", message);
    }

    public OptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OptionNotFoundException(Throwable cause) {
        super(cause);
    }
}
