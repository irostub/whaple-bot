package com.irostub.telegramtapbot.bot.command.complex.game.drop;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class DropGameService implements Commandable {
    @Override
    public void execute(CommandGatewayPack pack) {
        InlineKeyboardButton build1 = InlineKeyboardButton.builder()
                .callbackGame(new CallbackGame())
                .text("나를 눌러줘요!")
                .build();
        InlineKeyboardMarkup build2 = InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(build1)))
                .build();
        SendGame build = SendGame.builder()
                .chatId(ExtractUtils.getChatId(pack))
                .gameShortName("tap! tab! tap!")
                .replyMarkup(build2)
                .build();
        try {
            pack.getAbsSender().execute(build);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandType getSupports() {
        return CommandType.GAME_DROP_BOX;
    }
}
