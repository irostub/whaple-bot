package com.irostub.telegramtapbot.bot.command.complex;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException() {
        super();
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestaurantNotFoundException(Throwable cause) {
        super(cause);
    }
}
