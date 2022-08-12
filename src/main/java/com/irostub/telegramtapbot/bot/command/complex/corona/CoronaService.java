package com.irostub.telegramtapbot.bot.command.complex.corona;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import org.springframework.stereotype.Service;

@Service
public class CoronaService implements Commandable {
    @Override
    public void execute(CommandGatewayPack pack) {
        //TODO : 코로나 api 고장나있음
    }

    @Override
    public CommandType getSupports() {
        return CommandType.CORONA;
    }
}
