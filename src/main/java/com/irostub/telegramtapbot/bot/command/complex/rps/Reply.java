package com.irostub.telegramtapbot.bot.command.complex.rps;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

public class Reply implements SentCallback<Message> {
    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {

    }

    @Override
    public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {

    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {

    }
}
