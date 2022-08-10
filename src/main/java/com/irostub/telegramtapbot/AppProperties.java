package com.irostub.telegramtapbot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.HashSet;
import java.util.Set;

@Data
@PropertySources({
        @PropertySource(value="file:/home/irostub/hosting/telegram/application-prod.properties",
                ignoreResourceNotFound = true)
})
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Bot bot;
    private PublicApi publicApi;
    private Kakao kakao;

    @Data
    public static class Bot{
        private String token;
        private String botUsername;
        private Set<String> prefix = new HashSet<>();
    }

    @Data
    public static class PublicApi{
        private Weather weather;
        private Hangang hangang;

        @Data
        public static class Hangang{
            private String pageNumber;
            private String responseType;
            private String serviceType;
            private String url;
            private String token;
        }

        @Data
        public static class Weather{
            private String url;
            private String token;
        }
    }

    @Data
    public static class Kakao {
        private Geo geo;

        @Data
        public static class Geo{
            private String url;
            private String token;
            private String keywordUrl;
        }
    }


}
