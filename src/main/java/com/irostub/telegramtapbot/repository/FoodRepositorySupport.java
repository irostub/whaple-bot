package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Food;

import java.util.List;

public interface FoodRepositorySupport {
    List<Food> findByRandom(long limit);
}
