package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.hangang;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
public class PublicApiHangangService {
    private final AppProperties appProperties;
    private final RestTemplate restTemplate;
    private String url;

    @PostConstruct
    public void initUrl() {
        String responseType = appProperties.getPublicApi().getHangang().getResponseType();
        String url = appProperties.getPublicApi().getHangang().getUrl();
        String token = appProperties.getPublicApi().getHangang().getToken();
        String serviceType = appProperties.getPublicApi().getHangang().getServiceType();
        String pageNumber = appProperties.getPublicApi().getHangang().getPageNumber();

        StringBuilder builder = new StringBuilder(url);
        builder.append("/").append(token)
                .append("/").append(responseType)
                .append("/").append(serviceType)
                .append(pageNumber);
        this.url = builder.toString();
    }

    public RiverData sendHangangRequest() {
        HangangResponse hangangResponse = restTemplate.getForObject(url, HangangResponse.class);
        if(hangangResponse.getHangangData() == null){
            throw new RuntimeException("HangangResponse.getHangData() is null");
        }
        return hangangResponse.getHangangData();
    }
}
