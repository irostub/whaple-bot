package com.irostub.telegramtapbot.bot.thirdparty.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PublicApiWeatherServiceTest {
    @Test
    @DisplayName("date")
    void dateTest() {
        int x = 8;
        int y = 10;

        String format = String.format("%02d", x);
        String format1 = String.format("%02d", y);

        assertThat(format).isEqualTo("08");
        assertThat(format1).isEqualTo("10");
    }

    @Test
    @DisplayName("intsToString")
    void intsToStringTest() {
        String x = ""+1+3+4+6;
        assertThat(x).isEqualTo("1346");
    }
}