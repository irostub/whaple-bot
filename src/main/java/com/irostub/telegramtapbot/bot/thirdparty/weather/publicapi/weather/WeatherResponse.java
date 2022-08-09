package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Map<Category, FixedShortTermWeatherData> fixedWeatherDataMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("response")
    public void unpackNested(Map<String, Object> response) {
        Map<String, Object> body = (Map<String, Object>) response.get("body");
        Map<String, Object> items = (Map<String, Object>) body.get("items");
        List<ShortTermWeatherData> item = ((List<Object>) items.get("item")).stream()
                .map(o -> (Map<String, Object>) o)
                .map(ShortTermWeatherData::new)
                .collect(Collectors.toList());

        this.fixedWeatherDataMap = item.stream()
                .collect(Collectors.toMap(
                        ShortTermWeatherData::getCategory,
                        i -> new FixedShortTermWeatherData(i.getCategory(), i.getBaseDate(), i.getBaseTime(), i.obsrValue)));
    }

    @Data
    public static class FixedShortTermWeatherData {
        private String baseDate;
        private String baseTime;
        private String obsrValue;

        public FixedShortTermWeatherData(Category category, String baseDate, String baseTime, String obsrValue) {
            this.baseDate = baseDate;
            this.baseTime = baseTime;
            this.obsrValue = "";
            switch (category) {
                case T1H:
                    this.obsrValue = obsrValue + " ℃";
                    break;
                case RN1:
                    this.obsrValue = obsrValue + " mm";
                    break;
                case UUU:
                case VVV:
                    this.obsrValue = obsrValue + " m/s";
                    break;
                case REH:
                    this.obsrValue = obsrValue + " %";
                    break;
                case PTY: {
                    if (obsrValue.equals("0")) {
                        this.obsrValue = "없음";
                    } else if (obsrValue.equals("1")) {
                        this.obsrValue = "비";
                    } else if (obsrValue.equals("2")) {
                        this.obsrValue = "비/눈";
                    } else if (obsrValue.equals("3")) {
                        this.obsrValue = "눈";
                    } else if (obsrValue.equals("5")) {
                        this.obsrValue = "빗방울";
                    } else if (obsrValue.equals("6")) {
                        this.obsrValue = "빗방울눈날림";
                    } else if (obsrValue.equals("7")) {
                        this.obsrValue = "눈날림";
                    }
                }
                break;
                case VEC: {
                    double value = Double.parseDouble(obsrValue);
                    if ((value > 337.5 && value <= 360) || (value >= 0 && value <= 22.5)) {
                        this.obsrValue = "북풍";
                    } else if (value > 22.5 && value <= 67.5) {
                        this.obsrValue = "북동풍";
                    } else if (value > 67.5 && value <= 112.5) {
                        this.obsrValue = "동풍";
                    } else if (value > 112.5 && value <= 157.5) {
                        this.obsrValue = "남동풍";
                    } else if (value > 157.5 && value <= 202.5) {
                        this.obsrValue = "남풍";
                    } else if (value > 202.5 && value <= 247.5) {
                        this.obsrValue = "남서풍";
                    } else if (value > 247.5 && value <= 292.5) {
                        this.obsrValue = "서풍";
                    } else if (value > 292.5 && value <= 337.5) {
                        this.obsrValue = "북서풍";
                    }
                }
                break;
                case WSD:
                    this.obsrValue = obsrValue + " m/s";
                    break;
            }
        }
    }

    @Data
    public static class ShortTermWeatherData {
        private String baseDate;
        private String baseTime;
        private Category category;
        private String obsrValue;

        public ShortTermWeatherData() {
        }

        public ShortTermWeatherData(Map<String, Object> data) {
            this.baseDate = (String) data.get("baseData");
            this.baseTime = (String) data.get("baseTime");
            this.category = Category.valueOf((String) data.get("category"));
            this.obsrValue = (String) data.get("obsrValue");
        }
    }

    public enum Category {
        T1H,    //기온
        RN1,    //1시간 강수량
        UUU,    //동서바람성분
        VVV,    //남북바람성분
        REH,    //습도
        PTY,    //강수형태
        VEC,    //풍향
        WSD;    //풍속

        @JsonCreator
        public static Category jsonCreator(String value) {
            return Arrays.stream(Category.values())
                    .filter(t -> t.name().equals(value))
                    .findFirst()
                    .orElseThrow();
        }
    }
}
