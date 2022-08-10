package com.irostub.telegramtapbot.bot.thirdparty.gps.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoKeywordResponse {
    private List<Document> documents = new ArrayList<>();

    @Data
    public static class Document {
        private Double x;
        private Double y;
    }
}
