package com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather;

import lombok.Data;

@Data
public class FixedShortTermWeatherData {
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
