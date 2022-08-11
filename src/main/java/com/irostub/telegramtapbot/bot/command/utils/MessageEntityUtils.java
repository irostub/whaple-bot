package com.irostub.telegramtapbot.bot.command.utils;

import com.irostub.telegramtapbot.bot.MessageEntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

public class MessageEntityUtils {
    private static MessageEntity toBoldType(int offset, int length) {
        return MessageEntity.builder()
                .type(MessageEntityType.BOLD)
                .offset(offset)
                .length(length)
                .build();
    }

    private static MessageEntity toLinkType(int offset, int length, String url) {
        return MessageEntity.builder()
                .type(MessageEntityType.TEXT_LINK)
                .url(url)
                .offset(offset)
                .length(length)
                .build();
    }
    public static MessageEntity toBoldType(String originalMessage, String targetStr) {
        int idx = originalMessage.indexOf(targetStr);
        return toBoldType(idx, targetStr.length());
    }
    public static MessageEntity toLinkType(String originalMessage, String targetStr, String url) {
        int idx = originalMessage.indexOf(targetStr);
        return toLinkType(idx, targetStr.length(), url);
    }
}
