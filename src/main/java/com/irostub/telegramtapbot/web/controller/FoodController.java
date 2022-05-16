package com.irostub.telegramtapbot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food")
public class FoodController {
    @GetMapping
    public String foodListView() {
        return "hello";
    }

}
