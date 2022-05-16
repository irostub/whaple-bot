package com.irostub.telegramtapbot;

import com.irostub.telegramtapbot.domain.Food;
import com.irostub.telegramtapbot.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties
public class TelegramTapBotApplication {

    @Autowired
    FoodRepository foodRepository;

    public static void main(String[] args) {
        SpringApplication.run(TelegramTapBotApplication.class, args);
    }

    @PostConstruct
    void initData() {
        List<Food> foods = List.of(
                Food.createFood("부대찌개"),
                Food.createFood("돈까스"),
                Food.createFood("중국집"),
                Food.createFood("베트남 쌀국수"),
                Food.createFood("라멘"),
                Food.createFood("덴돈"),
                Food.createFood("따띠테이블"),
                Food.createFood("불백"),
                Food.createFood("이춘복 스시"),
                Food.createFood("뼈해장국"),
                Food.createFood("닭갈비"),
                Food.createFood("국수컵밥"),
                Food.createFood("KFC"),
                Food.createFood("버거킹"),
                Food.createFood("파스타"),
                Food.createFood("김밥"),
                Food.createFood("서브웨이"),
                Food.createFood("쉑쉑"));
        foodRepository.saveAllAndFlush(foods);
    }

}
