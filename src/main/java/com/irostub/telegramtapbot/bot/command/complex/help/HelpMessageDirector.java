package com.irostub.telegramtapbot.bot.command.complex.help;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class HelpMessageDirector {
    public static SendMessage helpMessage(CommandGatewayPack pack) {
        return SendMessage.builder()
                .chatId(ExtractUtils.getChatId(pack))
                .text("===== 도움말 =====\n" +
                        "사용 가능 접두문자 : '', ',', '.', '/'\n" +
                        "사용 가능한 명령어 : 도움, 밥\n" +
                        "예1) ㅂ\n"+
                        "예2) ,ㅂ\n"+
                        "예3) .도움\n"+
                        "예4) /ㄷㅇ")
                .build();
    }
}
