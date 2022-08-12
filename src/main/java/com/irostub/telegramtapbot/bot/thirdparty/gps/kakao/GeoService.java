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

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Slf4j
@Service
public class GeoService {
    private final RestTemplate restTemplate;
    private final AppProperties properties;
    private final GeoHttpRequestFactory requestFactory;

    public GeoResponse getGeo(String location) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(properties.getKakao().getGeo().getUrl())
                .queryParam("query", location).build(false);

        return sendRequest(uriComponents);
    }

    private GeoResponse sendRequest(UriComponents uriComponents) {
        HttpEntity<?> entity = requestFactory.getHttpEntity();
        ResponseEntity<GeoResponse> exchange = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, entity, GeoResponse.class);

        log.info("exchange: " + exchange.getBody());
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return exchange.getBody();
        }
        return null;
    }

    public GeoResponse getGeoKeyword(String location) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(properties.getKakao().getGeo().getKeywordUrl())
                .queryParam("query", location).build(false);

        return sendRequest(uriComponents);
    }
}
