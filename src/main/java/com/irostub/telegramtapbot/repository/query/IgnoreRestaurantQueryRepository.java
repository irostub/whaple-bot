package com.irostub.telegramtapbot.repository.query;

import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.domain.IgnoreRestaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.irostub.telegramtapbot.domain.QAccount.account;
import static com.irostub.telegramtapbot.domain.QIgnoreRestaurant.ignoreRestaurant;
import static com.irostub.telegramtapbot.domain.QRestaurant.restaurant;

@RequiredArgsConstructor
@Repository
public class IgnoreRestaurantQueryRepository {
    private final JPAQueryFactory query;

    public List<IgnoreRestaurant> getIgnoreList(Account findAccount) {
        return query.selectFrom(ignoreRestaurant)
                .join(ignoreRestaurant.restaurant, restaurant)
                .fetchJoin()
                .join(restaurant.account, account)
                .fetchJoin()
                .where(ignoreRestaurant.account.id.eq(findAccount.getId()))
                .fetch();
    }
}
