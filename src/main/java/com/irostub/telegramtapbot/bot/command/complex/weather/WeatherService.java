package com.irostub.telegramtapbot.bot.command.complex.weather;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoResponse;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoService;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.ConvertGpsAndGrid;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.PublicApiWeatherService;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.WeatherResponse;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.XYLatLng;
import com.irostub.telegramtapbot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService implements Commandable {
    private final GeoService geoService;
    private final PublicApiWeatherService weatherService;
    private final AccountRepository accountRepository;
    private final ConvertGpsAndGrid convertGpsAndGrid;

    @Override
    public void execute(CommandGatewayPack pack) {
        String address = pack.getOptions();
        if (StringUtils.isEmpty(address)) {
            //등록한 주소로 set
        }
        GeoResponse geo = geoService.getGeo(address);
        if(geo.getDocuments() != null && geo.getDocuments().size()>0){
            GeoResponse.Document document = geo.getDocuments().get(0);
            double x_gps = Double.parseDouble(document.getAddress().getX());
            double y_gps = Double.parseDouble(document.getAddress().getY());
            XYLatLng xyLatLng = convertGpsAndGrid.convertGRID_GPS(ConvertGpsAndGrid.TO_GRID, y_gps, x_gps);
            Map<WeatherResponse.Category, WeatherResponse.FixedShortTermWeatherData> categoryFixedShortTermWeatherDataMap = weatherService.sendCurrentWeatherRequest(xyLatLng.getXString(), xyLatLng.getYString());
            log.info("category = {}, data = {}", WeatherResponse.Category.RN1,categoryFixedShortTermWeatherDataMap.get(WeatherResponse.Category.RN1));
            SendMessage build = SendMessage.builder()
                    .chatId(ExtractUtils.getChatId(pack))
                    .text(categoryFixedShortTermWeatherDataMap.get(WeatherResponse.Category.RN1).getObsrValue())
                    .build();
            try {
                pack.getAbsSender().execute(build);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CommandType getSupports() {
        return CommandType.WEATHER;
    }
}
