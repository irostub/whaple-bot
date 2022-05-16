package com.irostub.telegramtapbot.bot.command.router;

import com.irostub.telegramtapbot.AppProperties;
import com.irostub.telegramtapbot.bot.command.CommandHolder;
import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.utils.CommandUtils;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommandGateway implements BotGateway{
    private final AppProperties properties;
    private final CommandHolder commandHolder;

    @Override
    public void route(RouteObjectDto routeObjectDto) {
        CommandType commandType = CommandUtils.parseCommandType(routeObjectDto.getPureCommand());
        Commandable commandable = commandHolder.getCommandable(commandType);

        commandable.execute(
                routeObjectDto.getUpdate(),
                routeObjectDto.getAbsSender(),
                routeObjectDto.getPureCommand());
    }
}
