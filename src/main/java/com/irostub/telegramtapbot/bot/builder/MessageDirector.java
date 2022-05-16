package com.irostub.telegramtapbot.bot.builder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public class MessageDirector {
    private final static int OFFSET_MENTION = 0;
    private final static String TEXT_MENTION_TYPE = "text_mention";

    public static SendMessage createNonMessage(String chatId, String pureCommand) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(pureCommand + "는 없는 명령어입니다.")
                .build();
    }

    private static MessageEntity createTextMentionMessageEntity(User user) {

        return MessageEntity.builder()
                .offset(OFFSET_MENTION)
                .length(user.getFirstName().length() +
                        (user.getLastName() != null ? user.getLastName().length() : 0))
                .type(TEXT_MENTION_TYPE)
                .user(user)
                .build();

    }

    public static SendMessage createSendMessage(User user, String menu) {
        return SendMessage.builder()
                .entities(List.of(createTextMentionMessageEntity(user)))
//                .chatId()
//                .text()
                .build();
    }
}
