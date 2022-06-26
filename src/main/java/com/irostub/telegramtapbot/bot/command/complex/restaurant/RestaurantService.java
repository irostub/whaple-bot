package com.irostub.telegramtapbot.bot.command.complex.restaurant;

import com.irostub.telegramtapbot.bot.MessageEntityType;
import com.irostub.telegramtapbot.bot.command.complex.*;
import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.domain.Restaurant;
import com.irostub.telegramtapbot.repository.AccountRepository;
import com.irostub.telegramtapbot.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RestaurantService implements Commandable {

    private final RestaurantRepository repository;
    private final AccountRepository accountRepository;

    enum SubCommand {
        CREATE("등록", "ㄷㄹ", "ㄷ", "추가", "ㅊㄱ"),
        DELETE("삭제", "ㅅㅈ", "ㅅ", "제거", "ㅈㄱ", "ㅈ"),
        RECOMMEND("추천", "ㅊㅊ", "ㅊ", "뭐먹", "ㅁㅁ", "ㅇㅈㅁ"),
        READ("목록", "ㅁㄹ", "리스트", "ㄹㅅㅌ", "종류", "ㅈㄹ");

        private final List<String> properties;

        SubCommand(String... properties) {
            this.properties = List.of(properties);
        }

        public static SubCommand of(String subCommand) {
            return Arrays.stream(SubCommand.values())
                    .parallel()
                    .filter(s -> s.properties.contains(subCommand))
                    .findFirst()
                    .orElseThrow(SubCommandNotFoundException::new);
        }
    }

    @Override
    public void execute(CommandGatewayPack pack) {
        String fixedOptions = replace(pack.getOptions());

        SubCommand subCommand = getSubCommand(fixedOptions);
        String subOptions = StringUtils.substringAfter(fixedOptions, " ");
        switch (subCommand) {
            case CREATE:
                create(subOptions, pack);
                break;
            case DELETE:
                delete(subOptions, pack);
                break;
            case RECOMMEND:
                recommend(subOptions, pack);
                break;
            case READ:
                read(pack);
                break;

        }
    }

    private void create(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Optional<Restaurant> optionalRestaurant = repository.findByName(restaurantName);

        MessageEntity messageEntity = MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(0)
                .length(getTextUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();

        if (optionalRestaurant.isPresent()) {
            SendMessage sendMessage = SendMessage.builder()
                    .chatId(pack.getUpdate().getMessage().getChatId().toString())
                    .entities(List.of(messageEntity))
                    .text(getTextUsername(pack.getUpdate()) + "님 " + restaurantName + "은(는) 이미 등록되어있어요.")
                    .build();

            try {
                pack.getAbsSender().execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            Account account = accountRepository
                    .findById(pack.getUpdate().getMessage().getFrom().getId())
                    .orElseThrow(AccountNotFoundException::new);
            Restaurant restaurant = Restaurant.newRestaurant(restaurantName, null, null, account);
            repository.save(restaurant);

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(pack.getUpdate().getMessage().getChatId().toString())
                    .entities(List.of(messageEntity))
                    .text(getTextUsername(pack.getUpdate()) + "님 " + restaurantName + "가(이) 등록되었어요.")
                    .build();

            try {
                pack.getAbsSender().execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void delete(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Restaurant findRestaurant = repository.findByName(restaurantName).orElseThrow();
        SendMessage message;

        if (isDeleteOwner(pack, findRestaurant) == false) {
            message = SendMessage.builder()
                    .chatId(pack.getUpdate().getMessage().getChatId().toString())
                    .text(findRestaurant.getAccount().getName() + "님이 등록한 " + restaurantName + "은(는) 등록자만 삭제할 수 있어요.")
                    .build();
        } else {
            message = SendMessage.builder()
                    .chatId(pack.getUpdate().getMessage().getChatId().toString())
                    .text(restaurantName + "이 삭제되었어요.")
                    .build();
            repository.delete(findRestaurant);
        }

        try {
            pack.getAbsSender().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean isWrongUsage(CommandGatewayPack pack, String restaurantName) {
        if(StringUtils.isEmpty(restaurantName)){
            SendMessage wrongMessage = SendMessage.builder()
                    .chatId(pack.getUpdate().getMessage().getChatId().toString())
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

    private boolean isDeleteOwner(CommandGatewayPack pack, Restaurant findRestaurant) {
        return findRestaurant.getCreatedBy().equals(pack.getUpdate().getMessage().getFrom().getId().toString());
    }

    private void read(CommandGatewayPack pack) {
        String restaurantNameList = repository.findAll().stream()
                .map(Restaurant::getName)
                .collect(Collectors.joining("\n"));

        AbsSender absSender = pack.getAbsSender();
        MessageEntity messageEntity = MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(5)
                .length(getTextUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(pack.getUpdate().getMessage().getChatId().toString())
                .entities(List.of(messageEntity))
                .text("=====" + getTextUsername(pack.getUpdate()) + "님 주문하신 밥집 목록 =====\n" + restaurantNameList)
                .build();

        try {
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void recommend(String subOptions, CommandGatewayPack pack) {
        Integer limit = Integer.parseInt(subOptions.strip());
        List<Restaurant> pickedRestaurants = repository.findByRandom(limit);
        String flatList = pickedRestaurants.stream()
                .map(restaurant -> restaurant.getName() + " - " +
                        restaurant.getAccount().getName() +
                        "님이 추천해준 메뉴에요!")
                .collect(Collectors.joining("\n"));

        MessageEntity messageEntity = MessageEntity.builder()
                .type(MessageEntityType.TEXT_MENTION)
                .offset(5)
                .length(getTextUsername(pack.getUpdate()).length())
                .user(pack.getUpdate().getMessage().getFrom())
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(pack.getUpdate().getMessage().getChatId().toString())
                .entities(List.of(messageEntity))
                .text("=====" + getTextUsername(pack.getUpdate()) + "님의 오늘의 메뉴 =====\n" + flatList)
                .build();
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getTextUsername(Update update) {
        return getLastName(update) + getFirstName(update);
    }

    private String getLastName(Update update) {
        String lastName = update.getMessage().getFrom().getLastName();
        if (org.springframework.util.StringUtils.hasText(lastName)) {
            return lastName;
        } else {
            return "";
        }
    }

    private String getFirstName(Update update) {
        return update.getMessage().getFrom().getFirstName();
    }

    @Override
    public CommandType getSupports() {
        return CommandType.RESTAURANT;
    }

    private String replace(String command) {
        if (StringUtils.isEmpty(command)) {
            return SubCommand.RECOMMEND.properties.get(0) + " 1";
        }

        if (canParseInteger(command)) {
            return SubCommand.RECOMMEND.properties.get(0) + " " + command;
        }

        return command;
    }

    private boolean canParseInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private SubCommand getSubCommand(String options) {
        return SubCommand.of(StringUtils.substringBefore(options, " "));
    }
}
