package com.irostub.telegramtapbot.bot.command.complex.hangang;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.hangang.PublicApiHangangService;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.hangang.RiverData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class HangangService implements Commandable {
    private final PublicApiHangangService service;
    //todo:redis cache 로 1시간 마다 캐시잡을 것
    @Override
    public void execute(CommandGatewayPack pack) {
        RiverData riverData = service.sendHangangRequest();
        SendMessage sendMessage = HangangMessageDirector.hangangMessage(pack, riverData);
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandType getSupports() {
        return CommandType.HANGANG;
    }
}
