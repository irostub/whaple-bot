package com.irostub.telegramtapbot.bot.command.complex;

import com.irostub.telegramtapbot.bot.TapBot;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@RequiredArgsConstructor
@Component
public class CommandGateway {

    private final CommandHolder commandHolder;

    public void route(String message, Update update, TapBot tapBot) {
        CommandType commandType = CommandType.of(separateCommand(message));
        Commandable commandService = commandHolder.getCommandService(commandType);
        commandService.execute(new CommandGatewayPack(separateOptions(message), update, tapBot));
    }

    private String separateOptions(String message) {
        if(message.contains(" ")){
            return StringUtils.substringAfter(message, " ").strip();
        }
        return "";
    }

    private String separateCommand(String message){
        if(message.contains(" ")){
            return StringUtils.split(message, " ", 2)[0];
        }
        return message;
    }
}
