package com.irostub.telegramtapbot.bot.command.complex.help;

import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;

import static com.irostub.telegramtapbot.bot.command.utils.MessageEntityUtils.*;

public class HelpMessageDirector {
    private static final String CLICK_HERE="이곳을 누르세요.";
    private static final String LUNCH_ROULETTE="점심 식사 룰렛";
    private static final String CHECK_WEATHER="날씨 확인";
    private static final String CHECK_HANGANG="한강 수온 확인";
    private static final String HELP_URL = "https://whaple.iro.ooo";

    private static List<MessageEntity> helpMessageEntity(String message) {
        MessageEntity clickEntity = toLinkType(message, CLICK_HERE, HELP_URL);
        MessageEntity lunchEntity = toBoldType(message, LUNCH_ROULETTE);
        MessageEntity weatherEntity = toBoldType(message, CHECK_WEATHER);
        MessageEntity hangangEntity = toBoldType(message, CHECK_HANGANG);

        return List.of(clickEntity, lunchEntity, weatherEntity, hangangEntity);
    }

    public static SendMessage helpMessage(CommandGatewayPack pack) {
        //TODO : 파일로 따로 관리할 수 있으면 편하겠다 \n도 안쳐도됨
        String message =
                "와플봇은 다기능 지원 봇입니다. 봇 공식 지원 문서가 필요하면 이곳을 누르세요.\n" +
                        "명령어 접두문자는 공백, 슬래시, 마침표 등을 지원합니다.\n\n\n" +
                        "각 명령어에 대한 설명은 [명령어] ? 로 확인해보세요!\n" +
                        "사용할 수 있는 명령어\n\n" +
                        LUNCH_ROULETTE+"\n"+
                        "밥\n"+
                        "밥 ?\n" +
                        "밥 추천\n" +
                        "밥 목록\n" +
                        "밥 추가\n" +
                        "밥 삭제\n" +
                        "밥 무시\n" +
                        "밥 복구\n\n" +
                        CHECK_WEATHER + "\n"+
                        "날씨 [지역]\n\n"+
                        CHECK_HANGANG + "\n"+
                        "한강";

        return SendMessage.builder()
                .chatId(ExtractUtils.getChatId(pack))
                .text(message)
                .entities(helpMessageEntity(message))
                .build();
    }
}
