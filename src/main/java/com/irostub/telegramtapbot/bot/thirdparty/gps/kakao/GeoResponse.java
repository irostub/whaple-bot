package com.irostub.telegramtapbot.bot.thirdparty.gps.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.tomcat.jni.Address;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse {
    List<Document> documents = new ArrayList<>();

    @Data
    public static class Document {
        private Address address;
    }

    @Data
    public static class Address{
        private String x;
        private String y;
    }
}
