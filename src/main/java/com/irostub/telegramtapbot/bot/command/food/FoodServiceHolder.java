package com.irostub.telegramtapbot.bot.command.food;

import com.irostub.telegramtapbot.bot.command.Commandable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FoodServiceHolder {
    private final Map<FoodCommandOption, FoodService> serviceMap = new HashMap<>();

    public FoodServiceHolder(List<FoodService> foodServiceList) {
        for (FoodService foodService : foodServiceList) {
            serviceMap.put(foodService.getFoodCommandOption(), foodService);
        }
    }

    public FoodService get(FoodCommandOption foodCommandOption) {
        return serviceMap.get(foodCommandOption);
    }
}
