package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Food;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.irostub.telegramtapbot.domain.QFood.*;

@RequiredArgsConstructor
@Repository
public class FoodRepositorySupportImpl implements FoodRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Food> findByRandom(long limit) {
        return jpaQueryFactory
                .selectFrom(food)
                .orderBy(NumberExpression.random().desc())
                .limit(limit)
                .fetch();
    }
}
