package com.irostub.telegramtapbot.bot.command;

@Deprecated
public class CommandNotFoundException extends NotFoundException{
    public CommandNotFoundException() {
        super();
    }

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFoundException(Throwable cause) {
        super(cause);
    }
}
