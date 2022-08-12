package com.irostub.telegramtapbot.bot.command.complex.rps;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendGame;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Slf4j
@Service
public class RpsService implements Commandable {
    @Override
    public void execute(CommandGatewayPack pack) {

    }

    public void hello(){

    }
    @Override
    public CommandType getSupports() {
        return CommandType.RPS;
    }
    //RPS 7, RPS 11, RPS 25(이건 좀 ...)
}
/*
log.info("why?????");
        KeyboardRow s = new KeyboardRow();
        s.add("가위");
        KeyboardRow r = new KeyboardRow();
        r.add("바위");
        KeyboardRow p = new KeyboardRow();
        p.add("보");
        CallbackGame callbackGame = new CallbackGame();
        InlineKeyboardButton.builder()
                .text("abc")
                .callbackGame()
                .build();

        ReplyKeyboard replyKeyboard = ReplyKeyboardMarkup.builder()
                .keyboard(List.of(r,p,s))
                .build();

        ForceReplyKeyboard forceReplyKeyboard = ForceReplyKeyboard.builder()
                .inputFieldPlaceholder("플레이스 홀더라는데?")
                .build();
        SendMessage testMessage = SendMessage.builder()
                .replyMarkup(replyKeyboard)
                .text("뭐야 뭔기능이야??")
                .chatId(ExtractUtils.getChatId(pack))
                .build();
        SendMessage testMessage2 = SendMessage.builder()
                .chatId(ExtractUtils.getChatId(pack))
                .text("왜불러")
                .build();
        try {
            AbsSender absSender = pack.getAbsSender();
            absSender.execute(testMessage);
        }catch (Exception e) {
            e.printStackTrace();
        }
*/