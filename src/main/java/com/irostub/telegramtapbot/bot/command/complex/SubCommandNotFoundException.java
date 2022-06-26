package com.irostub.telegramtapbot.bot.command.complex;

public class SubCommandNotFoundException extends RuntimeException{
    public SubCommandNotFoundException() {
        super();
    }

    public SubCommandNotFoundException(String message) {
        super(message);
    }

    public SubCommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubCommandNotFoundException(Throwable cause) {
        super(cause);
    }
}
