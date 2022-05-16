package com.irostub.telegramtapbot.bot;

import com.irostub.telegramtapbot.AppProperties;
import com.irostub.telegramtapbot.bot.command.router.CommandGateway;
import com.irostub.telegramtapbot.bot.command.router.RouteObjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RequiredArgsConstructor
@Component
public class TapBot extends TelegramLongPollingBot {

    private final AppProperties properties;
    private final CommandGateway commandGateway;

    //봇 초기화 [봇 토큰]
    @Override
    public String getBotToken() {
        return properties.getBot().getToken();
    }

    //봇 초기화 [봇 소유자명]
    @Override
    public String getBotUsername() {
        return properties.getBot().getBotUsername();
    }

    @Override
    public void onUpdateReceived(Update update) {
        //개인, 그룹에서의 메세지가 아닐 경우
        if (!isMessageUpdate(update)) {
            return;
        }

        String rawMessage = update.getMessage().getText();
        String outerPrefix = separateOuterPrefix(rawMessage);

        //봇 prefix 가 없는 경우
        if (outerPrefix == null) {
            return;
        }
        log.info("[TapBot][onUpdateReceived] received message = {}, user = {}",
                rawMessage,
                update.getMessage().getFrom().toString());

        String withoutPrefix = getMessageWithoutPrefix(rawMessage, outerPrefix.length());

        //봇 명령줄이 없는 경우
        if (withoutPrefix == null) {
            return;
        }

        //커맨드 게이트웨이에게 전달
        commandGateway.route(new RouteObjectDto(update, this, withoutPrefix));
    }

    @Override
    public void onRegister() {
        super.onRegister();
        log.debug("register this bot");
    }

    private String separateOuterPrefix(String message) {
        return properties.getBot().getPrefix().stream()
                .parallel()
                .filter(prefix -> message.strip().startsWith(prefix))
                .findAny()
                .orElse(null);
    }

    private String getMessageWithoutPrefix(String message, int prefixLength){
        return message.substring(prefixLength).strip();
    }

    private boolean isMessageUpdate(Update update) {
        return update.hasMessage() && (update.getMessage().isGroupMessage() || update.getMessage().isUserMessage());
    }
}

//
//            if (!StringUtils.isEmpty(count)) {
//                try {
//                    limit = Long.parseLong(count);
//                } catch (NumberFormatException e) {
//                    log.warn("count is not number = {}, ex = {}", count, e);
//                }
//            }

//            List<String> foodList = foodRepository.findByRandom(limit).stream()
//                    .map(Food::getName)
//                    .collect(Collectors.toList());
//
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(update.getMessage().getChat().getId().toString());
//            sendMessage.enableMarkdown(true);
//            sendMessage.setText(String.join("\n", foodList));

//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }


/**MessageEntity entity = MessageEntity.builder()
 .offset(1)
 .length(8)
 .type("mention")
 .text("@irostub")
 .build();
 MessageEntity url = MessageEntity.builder()
 .type()
 .url("https://www.google.com")
 .length(3)
 .offset(0)
 .build();
 SendMessage sendMessage = new SendMessage();
 sendMessage.setEntities(List.of(url));
 sendMessage.setChatId(update.getMessage().getChat().getId().toString());
 sendMessage.setText("ggg");
 log.info("messageEntities={}", update.getMessage().getEntities());

 try {
 execute(sendMessage);
 } catch (TelegramApiException e) {
 e.printStackTrace();
 }**/


/**
 *
 MessageEntity entity = MessageEntity.builder()
 .type("text_mention")
 .offset(0)
 .length(4)
 .user(update.getMessage().getFrom())
 .build();
 SendMessage sendMessage = new SendMessage();
 sendMessage.setChatId(update.getMessage().getChat().getId().toString());
 sendMessage.setEntities(List.of(entity));
 sendMessage.setText("동민 신asdfasdfasdfasdf");
 */