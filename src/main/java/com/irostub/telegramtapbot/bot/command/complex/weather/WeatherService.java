package com.irostub.telegramtapbot.bot.command.complex.weather;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.bot.command.complex.Commandable;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoCache;
import com.irostub.telegramtapbot.bot.thirdparty.gps.kakao.GeoResponse;
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
    private final PublicApiWeatherService weatherService;
    private final AccountRepository accountRepository;
    private final ConvertGpsAndGrid convertGpsAndGrid;
    private final GeoCache geoCache;

    @Override
    public void execute(CommandGatewayPack pack) {
        String address = pack.getOptions();
        if (StringUtils.isEmpty(address)) {
            //등록한 주소로 set
        }

        GeoResponse geo = geoCache.getGeoResponse(address);

        if (!hasGeoResult(pack, geo)) return;

        String addressName = geo.getDocuments().get(0).getAddressName();
        Double x_gps = geo.getDocuments().get(0).getX();
        Double y_gps = geo.getDocuments().get(0).getY();

        XYLatLng xyLatLng = convertGpsAndGrid.convertGRID_GPS(ConvertGpsAndGrid.TO_GRID,y_gps , x_gps);

        Map<Category, FixedShortTermWeatherData> fixedShortTermWeatherDataMap = weatherService
                .sendCurrentWeatherRequest(xyLatLng.getXString(), xyLatLng.getYString());

        SendMessage sendMessage = WeatherMessageDirector.weatherMessage(pack, fixedShortTermWeatherDataMap, addressName);
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean hasGeoResult(CommandGatewayPack pack, GeoResponse geo) {
        if (geo.getDocuments() == null || geo.getDocuments().isEmpty()) {
            SendMessage build = SendMessage.builder()
                    .chatId(ExtractUtils.getChatId(pack))
                    .text("지역 주소를 찾을 수 없어요.")
                    .build();
            try {
                pack.getAbsSender().execute(build);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override
    public CommandType getSupports() {
        return CommandType.WEATHER;
    }
}
