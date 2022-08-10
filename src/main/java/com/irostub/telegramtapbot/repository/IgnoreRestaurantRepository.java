package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.domain.IgnoreRestaurant;
import com.irostub.telegramtapbot.domain.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IgnoreRestaurantRepository extends JpaRepository<IgnoreRestaurant, Long> {
    Optional<IgnoreRestaurant> findByAccountAndRestaurant(Account account, Restaurant restaurant);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    List<IgnoreRestaurant> findAllByAccount(Account account);
}
