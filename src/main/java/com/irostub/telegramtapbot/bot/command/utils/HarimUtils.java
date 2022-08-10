package com.irostub.telegramtapbot.bot.command.utils;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.domain.Restaurant;

import java.util.Arrays;
import java.util.List;

public class HarimUtils {

    public static Long HARIM_ID = 2010885427L;
    public static List<Long> ignoredList = Arrays.asList(20L, 24L, 25L, 27L);

    public static boolean isHarim(CommandGatewayPack pack) {
        Long id = pack.getUpdate().getMessage().getFrom().getId();
        if (id.equals(HARIM_ID))
            return true;
        return false;
    }

    public static boolean isNotIgnoredRestaurant(Restaurant restaurant) {
        if (ignoredList.contains(restaurant.getId()))
            return false;
        return true;
    }
}
