package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Restaurant;
import com.irostub.telegramtapbot.repository.query.RestaurantRepositorySupport;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantRepositorySupport {
    @EntityGraph(attributePaths = {"account"})
    Optional<Restaurant> findByName(String name);
}
