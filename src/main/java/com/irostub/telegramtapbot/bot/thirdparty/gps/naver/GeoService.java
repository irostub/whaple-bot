package com.irostub.telegramtapbot.bot.thirdparty.gps.naver;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Slf4j
@Service
public class GeoService {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public void getGeo(String location) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + appProperties.getNaver().getGeo().getToken());

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(appProperties.getNaver().getGeo().getUrl())
                .queryParam("query", location);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<GeoResponse> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, GeoResponse.class);

        log.info("exchange: " + exchange.getBody());
    }
}
