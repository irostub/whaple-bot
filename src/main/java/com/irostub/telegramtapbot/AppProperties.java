package com.irostub.telegramtapbot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter(value= AccessLevel.PACKAGE)
@Profile({"prod","local"})
@PropertySources({
        @PropertySource(value="file:/home/irostub/hosting/telegram/application-prod.yml", ignoreResourceNotFound = true)
})
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private Bot bot;
    private PublicApi publicApi;
    private Naver naver;

    @Getter @Setter
    public static class Bot{
        private String token;
        private String botUsername;
        private Set<String> prefix = new HashSet<>();
    }

    @Getter @Setter
    public static class PublicApi{
        private Weather weather;
        private Hangang hangang;

        @Getter @Setter
        public static class Hangang{
            private String pageNumber;
            private String responseType;
            private String serviceType;
            private String url;
            private String token;
        }

        @Getter @Setter
        public static class Weather{
            private String url;
            private String token;
        }
    }

    @Getter @Setter
    public static class Naver{
        private Geo geo;

        @Getter @Setter
        public static class Geo{
            private String url;
            private String token;
        }
    }


}
