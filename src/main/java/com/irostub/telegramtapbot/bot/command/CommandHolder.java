package com.irostub.telegramtapbot.bot.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandHolder {
    private final Map<CommandType, Commandable> commandableMap = new HashMap<>();

    public CommandHolder(List<Commandable> commandableList) {
        for (Commandable commandable : commandableList) {
            commandableMap.put(commandable.getCommandType(), commandable);
        }
    }

    public Commandable getCommandable(CommandType commandType){
        return commandableMap.get(commandType);
    }
}
