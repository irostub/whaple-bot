package com.irostub.telegramtapbot.bot.command.complex.weather;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoKeywordResponse;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoResponse;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoService;
import com.irostub.telegramtapbot.bot.thirdparty.weather.publicapi.weather.*;
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
        Double x_gps = null;
        Double y_gps = null;

        if (geo.getDocuments() != null && geo.getDocuments().size() > 0) {
            GeoResponse.Document document = geo.getDocuments().get(0);
            x_gps = Double.parseDouble(document.getAddress().getX());
            y_gps = Double.parseDouble(document.getAddress().getY());
        } else {
            GeoKeywordResponse geoKeyword = geoService.getGeoKeyword(address);
            if (geoKeyword.getDocuments() != null && geoKeyword.getDocuments().size() > 0) {
                x_gps = geoKeyword.getDocuments().get(0).getX();
                y_gps = geoKeyword.getDocuments().get(0).getY();
            }
        }

        //주소, 키워드 주소로 아무것도 찾을 수 없을 때 반환
        if (x_gps == null || y_gps == null) {
            SendMessage build = SendMessage.builder()
                    .chatId(ExtractUtils.getChatId(pack))
                    .text("지역 주소를 찾을 수 없습니다.")
                    .build();
            try {
                pack.getAbsSender().execute(build);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return;
        }

        XYLatLng xyLatLng = convertGpsAndGrid.convertGRID_GPS(ConvertGpsAndGrid.TO_GRID, y_gps, x_gps);

        Map<Category, FixedShortTermWeatherData> fixedShortTermWeatherDataMap = weatherService
                .sendCurrentWeatherRequest(xyLatLng.getXString(), xyLatLng.getYString());

        log.info("category = {}, data = {}", Category.RN1, fixedShortTermWeatherDataMap.get(Category.RN1));

        SendMessage sendMessage = WeatherMessageDirector.weatherMessage(pack,fixedShortTermWeatherDataMap);
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandType getSupports() {
        return CommandType.WEATHER;
    }
}
