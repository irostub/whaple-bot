package com.irostub.telegramtapbot.bot.command.complex.hangang;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.hangang.RiverData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.format.DateTimeFormatter;

public class HangangMessageDirector {
    public static SendMessage hangangMessage(CommandGatewayPack pack, RiverData riverData) {
        String strDate = riverData.getMeasureDateTime()
                .format(DateTimeFormatter.ofPattern("MMdd HH:mm"));
        StringBuilder builder = new StringBuilder();
        builder
                .append("[")
                .append(strDate)
                .append("]")
                .append(" 한강 수온 ")
                .append(riverData.getTemperature())
                .append("°C");
        return SendMessage.builder()
                .chatId(ExtractUtils.getChatId(pack))
                .text(builder.toString())
                .build();
    }
}
