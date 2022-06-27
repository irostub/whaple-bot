package com.irostub.telegramtapbot.bot.thirdparty.gps.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse {
    List<Document> documents = new ArrayList<>();

    @Data
    public static class Document {
        private String x;
        private String y;
    }
}
