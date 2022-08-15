package com.irostub.telegramtapbot.web.controller.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DropboxController {
    @GetMapping
    public String game(){
        return "index";
    }

    @GetMapping("/tailwind")
    public String tailwind(){
        return "tailwind";
    }

    @GetMapping("/test")
    public String test() {
        return "myhtml";
    }
}
