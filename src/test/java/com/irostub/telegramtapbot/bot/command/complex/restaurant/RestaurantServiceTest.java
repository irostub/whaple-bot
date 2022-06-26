package com.irostub.telegramtapbot.bot.command.complex.restaurant;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class RestaurantServiceTest {
    @Test
    @DisplayName("StringSplitNull")
    void StringSplitNullTest() {
        String s1 = StringUtils.substringAfter("등록", " ");
        String s2 = StringUtils.substringAfter("등록 hi", " ");
    }

    @Test
    @DisplayName("beforeAfterString")
    void beforeAfterStringTest() {
        String s = StringUtils.substringBefore("hello 1b 3", " ");
        String s1 = StringUtils.substringBefore("hello", " ");
        System.out.println("s = " + s);
        System.out.println("s = " + s1);
    }
}