package com.irostub.telegramtapbot.bot.command.router;

import com.irostub.telegramtapbot.AppProperties;
import com.irostub.telegramtapbot.bot.command.CommandHolder;
import com.irostub.telegramtapbot.bot.command.CommandType;
import com.irostub.telegramtapbot.bot.command.utils.CommandUtils;
import com.irostub.telegramtapbot.bot.command.Commandable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandGateway implements BotGateway{
    private final AppProperties properties;
    private final CommandHolder commandHolder;

    @Override
    public void route(RouteObjectDto routeObjectDto) {
        log.info("without bot prefix = {}", routeObjectDto.getPureCommand());
        CommandType commandType = CommandUtils.parseCommandType(routeObjectDto.getPureCommand());
        Commandable commandable = commandHolder.getCommandable(commandType);

        String withoutCommandPrefix = getWithoutCommandPrefix(routeObjectDto.getPureCommand());
        log.info("without command prefix = {}", withoutCommandPrefix);
        commandable.run(
                routeObjectDto.getUpdate(),
                routeObjectDto.getAbsSender(),
                withoutCommandPrefix);
    }

    private String getWithoutCommandPrefix(String withoutBotPrefix){
        if(withoutBotPrefix.contains(" ")){
            return withoutBotPrefix.substring(withoutBotPrefix.indexOf(" ")).strip();
        }
        else{
            return "";
        }
    }
}
