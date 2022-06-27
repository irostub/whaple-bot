package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class WeatherService {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public void sendCurrentWeatherRequest(String nx, String ny){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        LocalDateTime now = LocalDateTime.now();
        String baseDateFormatted = "" + now.getYear() + now.getMonthValue() + now.getDayOfMonth();
        String baseTimeFormatted = String.format("%02d", now.getHour())+"00";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(appProperties.getPublicApi().getWeather().getUrl())
                .queryParam("serviceKey", appProperties.getPublicApi().getWeather().getToken())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", baseDateFormatted)
                .queryParam("base_time", baseTimeFormatted)
                .queryParam("nx", nx)
                .queryParam("ny", ny);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        log.info("ResponseEntity: " + exchange.getBody());
    }
}
