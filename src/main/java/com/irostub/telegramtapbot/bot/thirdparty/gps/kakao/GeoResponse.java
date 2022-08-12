package com.irostub.telegramtapbot.bot.thirdparty.gps.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.tomcat.jni.Address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoResponse {
    private List<Document> documents = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("documents")
    public void deserialize(List<Object> documents) {
        this.documents = documents.stream()
                .map(o->(Map<String, Object>) o)
                .map(Document::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class Document {
        private String addressName;
        private Double x;
        private Double y;

        public Document(Map<String, Object> item) {
            this.addressName = (String)item.get("address_name");
            this.x = Double.parseDouble((String)item.get("x"));
            this.y = Double.parseDouble((String)item.get("y"));
        }
    }
}
