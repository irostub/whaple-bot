package com.irostub.telegramtapbot.bot.thirdparty.gps.kakao;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
@Service
public class GeoService {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public GeoResponse getGeo(String location) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + appProperties.getKakao().getGeo().getToken());

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(appProperties.getKakao().getGeo().getUrl())
                .queryParam("query", location).build(false);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<GeoResponse> exchange = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, GeoResponse.class);

        log.info("exchange: " + exchange.getBody());
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange.getBody();
        }
        return null;
    }
}
