package com.irostub.telegramtapbot.bot.command.complex;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandHolder {
    private final Map<CommandType, Commandable> commandServicesMap = new HashMap<>();

    public CommandHolder(List<Commandable> commandableList) {
        for (Commandable commandable : commandableList) {
            commandServicesMap.put(commandable.getSupports(), commandable);
        }
    }

    public Commandable getCommandService(CommandType commandType){
        return commandServicesMap.get(commandType);
    }
}
