package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.corona;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PublicApiCoronaService {
    private final AppProperties properties;

    public void sendCoronaApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

//        UriComponents uriComponents = UriComponentsBuilder.fromUriString(properties.getPublicApi().getCorona().getUrl())
//                .
//                .build(false);


    }
}
