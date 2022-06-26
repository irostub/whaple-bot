package com.irostub.telegramtapbot.repository.query;

import com.irostub.telegramtapbot.domain.Restaurant;

import java.util.List;

public interface RestaurantRepositorySupport {
    List<Restaurant> findByRandom(Integer limit);
}
