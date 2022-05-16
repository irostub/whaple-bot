package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositorySupport{
}
