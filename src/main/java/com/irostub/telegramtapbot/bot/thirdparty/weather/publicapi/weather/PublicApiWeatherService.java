package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather;

import com.irostub.telegramtapbot.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class PublicApiWeatherService {
    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public void sendCurrentWeatherRequest(String nx, String ny){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        LocalDateTime now = LocalDateTime.now();
        String baseDateFormatted = "" + now.getYear() + now.getMonthValue() + now.getDayOfMonth();
        String baseTimeFormatted = String.format("%02d", now.getHour())+"00";
        Map<String, Object> data = new HashMap<>();
        data.put("serviceKey",appProperties.getPublicApi().getWeather().getToken());
        data.put("pageNo",1);
        data.put("numOfRows",10);
        data.put("dataType","JSON");
        data.put("base_date", baseDateFormatted);
        data.put("base_time", baseTimeFormatted);
        data.put("nx",nx);
        data.put("ny",ny);

        URI uri = null;
        try {
            uri = new URI(appProperties.getPublicApi().getWeather().getUrl() + "/getUltraSrtNcst?" +
                    "serviceKey=" + appProperties.getPublicApi().getWeather().getToken() + "&" +
                    "pageNo=1&numOfRows=10&dataType=JSON&base_date=" + baseDateFormatted + "&base_time=" + baseTimeFormatted + "&" +
                    "nx=" + nx + "&ny=" + ny);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpEntity<?> entity = new HttpEntity<>(headers);
        WeatherResponse forObject = restTemplate.getForObject(uri, WeatherResponse.class);
        log.info("ResponseEntity: " + forObject);
    }
}
