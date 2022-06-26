package com.irostub.telegramtapbot.bot.command.complex;

public interface Commandable {

    void execute(CommandGatewayPack pack);
    CommandType getSupports();
}
