package com.irostub.telegramtapbot.bot;

import com.irostub.telegramtapbot.AppProperties;
import com.irostub.telegramtapbot.bot.command.complex.CommandGateway;
import com.irostub.telegramtapbot.bot.command.complex.CommandType;
import com.irostub.telegramtapbot.config.UserInfoHolder;
import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;


@Slf4j
@RequiredArgsConstructor
@Component
public class TapBot extends TelegramLongPollingBot {
    private final AppProperties properties;
    private final CommandGateway commandGateway;
    private final AccountRepository accountRepository;

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
        if (isGroupOrUserMessage(update) == false) {
            return;
        }

        String rawMessage = update.getMessage().getText();
        if(containsWhapleBotPrefix(rawMessage) == false &&
                isPureCommand(rawMessage) == false){
            return;
        }

        log.info("update = {}", update);

        User user = update.getMessage().getFrom();
        UserInfoHolder userInfoHolder = new UserInfoHolder();
        userInfoHolder.setUser(user);

        createAccountIfNotExists(user);

        log.info("[TapBot][onUpdateReceived] received message = {}, user = {}", rawMessage, update.getMessage().getFrom().toString());

        String message = rawMessage;

        if(isPureCommand(rawMessage) == false){
            message= separateBotPrefix(rawMessage);
        }

        commandGateway.route(message, update, this);
    }

    private void createAccountIfNotExists(User user) {
        if(existsAccount(user) == false){
            Account account = Account.create(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
            accountRepository.saveAndFlush(account);
        }
    }

    private boolean existsAccount(User user) {
        return accountRepository.existsById(user.getId());
    }

    private String separateBotPrefix(String rawMessage) {
        return rawMessage.substring(1);
    }

    private boolean isPureCommand(String rawMessage) {
        String stripedMessage = rawMessage.strip();
        if(stripedMessage.contains(" ")){
            String[] split = StringUtils.split(stripedMessage, " ", 2);
            return CommandType.contains(split[0]);
        }else{
            return CommandType.contains(stripedMessage);
        }
    }

    private boolean containsWhapleBotPrefix(String rawMessage) {
        return properties.getBot().getPrefix().contains(rawMessage.substring(0, 1));
    }

    @Override
    public void onRegister() {
        super.onRegister();

        log.info("register this bot");
    }

    private boolean isGroupOrUserMessage(Update update) {
        Message message = update.getMessage();
        return update.hasMessage() &&
                (message.isGroupMessage() || message.isUserMessage() || message.isSuperGroupMessage()) &&
                message.getFrom().getIsBot() == false;
    }
}