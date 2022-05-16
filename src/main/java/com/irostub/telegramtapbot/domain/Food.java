package com.irostub.telegramtapbot.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Food {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String url;

    //나중에 필요하면 넣자
    //@Enumerated(EnumType.STRING)
    //private FoodType foodType;

    protected Food() {}

    public static Food createFood(String name) {
        Food food = new Food();
        food.name = name;
        return food;
    }
}
