package com.irostub.telegramtapbot.bot.command.complex.birthday;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.PublicApiWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BirthdayService implements Commandable {

    @Override
    public void execute(CommandGatewayPack pack) {

    }

    @Override
    public CommandType getSupports() {
        return CommandType.BIRTHDAY;
    }
}
