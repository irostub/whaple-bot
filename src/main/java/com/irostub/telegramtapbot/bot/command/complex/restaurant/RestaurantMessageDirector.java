package com.irostub.telegramtapbot.bot.command.complex.restaurant;

import com.irostub.telegramtapbot.bot.MessageEntityType;
import com.irostub.telegramtapbot.bot.command.complex.CommandGatewayPack;
import com.irostub.telegramtapbot.bot.command.utils.ExtractUtils;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static com.irostub.telegramtapbot.bot.command.utils.ExtractUtils.*;
import static com.irostub.telegramtapbot.bot.command.utils.ExtractUtils.extractUsername;

public class RestaurantMessageDirector {
    
    
    /*
        recommend
    */
    private static MessageEntity recommendMessageEntity(CommandGatewayPack pack) {
        return MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(5)
                .length(extractUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();
    }

    public static SendMessage recommendMessage(CommandGatewayPack pack, String recommendList) {
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .entities(List.of(recommendMessageEntity(pack)))
                .text("=====" + extractUsername(pack.getUpdate()) + "님의 오늘의 메뉴 =====\n" + recommendList)
                .build();
    }

    /*
        create
    */
    private static MessageEntity createRestaurantMessageEntity(CommandGatewayPack pack) {
        return MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(0)
                .length(extractUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();
    }

    public static SendMessage createExistsRestaurantMessage(CommandGatewayPack pack, String restaurantName) {
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .entities(List.of(createRestaurantMessageEntity(pack)))
                .text(extractUsername(pack.getUpdate()) + "님 " + restaurantName + "은(는) 이미 등록되어있어요.")
                .build();
    }

    public static SendMessage createRestaurantMessage(CommandGatewayPack pack, String restaurantName) {
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .entities(List.of(createRestaurantMessageEntity(pack)))
                .text(extractUsername(pack.getUpdate()) + "님 " + restaurantName + "가(이) 등록되었어요.")
                .build();
    }

    /*
        delete
    */
    public static SendMessage deleteNotOwnerRestaurantMessage(CommandGatewayPack pack, String accountName, String restaurantName) {
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .text(accountName + "님이 등록한 " + restaurantName + "은(는) 등록자만 삭제할 수 있어요.")
                .build();
    }


    public static SendMessage deleteRestaurantMessage(CommandGatewayPack pack, String restaurantName) {
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .text(restaurantName + "이 삭제되었어요.")
                .build();
    }

    /*
        read
    */
    private static MessageEntity readRestaurantListMessageEntity(CommandGatewayPack pack){
        return MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(5)
                .length(extractUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();
    }

    public static SendMessage readRestaurantListMessage(CommandGatewayPack pack, String restaurantNameList){
        return SendMessage.builder()
                .chatId(getChatId(pack))
                .entities(List.of(readRestaurantListMessageEntity(pack)))
                .text("=====" + extractUsername(pack.getUpdate()) + "님 주문하신 밥집 목록 =====\n" + restaurantNameList)
                .build();
    }


    /*
        help
    */
    public static SendMessage helpMessage(CommandGatewayPack pack){
        return SendMessage.builder()
                .parseMode("markdown")
                .chatId(getChatId(pack))
                .text("*추천* ㅂ \\[(옵션)추천, ㅊㅊ, ㅊ, 뭐먹, ㅁㅁ] \\[(옵션)개수]\n" +
                        "   예) ㅂ\n\n" +
                        "*등록* ㅂ \\[등록, ㄷㄹ, ㄷ, 추가, ㅊㄱ] \\[가게명]\n" +
                        "   예) ㅂ ㄷㄹ 춘천닭갈비\n\n" +
                        "*삭제* ㅂ \\[삭제, ㅅㅈ, ㅅ, 제거, ㅈㄱ] \\[가게명]\n" +
                        "   예) ㅂ ㅅㅈ 춘천닭갈비\n\n" +
                        "*목록* \\[목록, ㅁㄹ, 리스트, ㄹㅅㅌ]\n" +
                        "   예) ㅂ ㅁㄹ\n\n" +
                        "*도움* \\[?, 도움말, 도움, ㄷㅇ]\n" +
                        "   예) ㅂ ?")
                .build();
    }


    /*
        common
    */
    public static boolean isWrongUsage(CommandGatewayPack pack, String restaurantName) {
        if (StringUtils.isEmpty(restaurantName)) {
            SendMessage wrongMessage = SendMessage.builder()
                    .chatId(getChatId(pack))
                    .text("명령어가 잘못된 것 같아요! 밥 명령어의 도움말을 보려면 ㅂ ? 를 입력해보세요.")
                    .build();
            try {
                pack.getAbsSender().execute(wrongMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


}

