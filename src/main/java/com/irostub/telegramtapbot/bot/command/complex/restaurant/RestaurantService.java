package com.irostub.telegramtapbot.bot.command.complex.restaurant;

import com.irostub.telegramtapbot.bot.command.complex.*;
import com.irostub.telegramtapbot.bot.command.utils.ParseUtils;
import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.domain.Restaurant;
import com.irostub.telegramtapbot.repository.AccountRepository;
import com.irostub.telegramtapbot.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.irostub.telegramtapbot.bot.command.complex.restaurant.RestaurantMessageDirector.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantService implements Commandable {

    private final RestaurantRepository repository;
    private final AccountRepository accountRepository;

    @Override
    public void execute(CommandGatewayPack pack) {
        String fixedOptions = replace(pack.getOptions());

        RestaurantSubCommand restaurantSubCommand = getSubCommand(fixedOptions);
        String subOptions = StringUtils.substringAfter(fixedOptions, " ");
        switch (restaurantSubCommand) {
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
            case HELP:
                help(pack);
        }
    }

    private void help(CommandGatewayPack pack) {
        SendMessage sendMessage = helpMessage(pack);
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void create(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Optional<Restaurant> optionalRestaurant = repository.findByName(restaurantName);

        if (optionalRestaurant.isPresent()) {
            SendMessage sendMessage = createExistsRestaurantMessage(pack, restaurantName);

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

            SendMessage sendMessage = createRestaurantMessage(pack, restaurantName);

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
            message = deleteNotOwnerRestaurantMessage(pack, findRestaurant.getAccount().getName(), restaurantName);
        } else {
            message = deleteRestaurantMessage(pack, restaurantName);
            repository.delete(findRestaurant);
        }

        try {
            pack.getAbsSender().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private boolean isDeleteOwner(CommandGatewayPack pack, Restaurant findRestaurant) {
        return findRestaurant.getCreatedBy().equals(pack.getUpdate().getMessage().getFrom().getId().toString());
    }

    private void read(CommandGatewayPack pack) {
        String restaurantNameList = repository.findAll().stream()
                .map(Restaurant::getName)
                .collect(Collectors.joining("\n"));

        SendMessage sendMessage = readRestaurantListMessage(pack, restaurantNameList);

        try {
            pack.getAbsSender().execute(sendMessage);
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
                        "님이 추천해준 메뉴에요!\n" + restaurant.getUrl())
                .collect(Collectors.joining("\n"));

        SendMessage sendMessage = recommendMessage(pack, flatList);
        try {
            pack.getAbsSender().execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandType getSupports() {
        return CommandType.RESTAURANT;
    }

    private String replace(String command) {
        if (StringUtils.isEmpty(command)) {
            return RestaurantSubCommand.RECOMMEND.getProperties().get(0) + " 1";
        }

        if (ParseUtils.canParseInteger(command)) {
            return RestaurantSubCommand.RECOMMEND.getProperties().get(0) + " " + command;
        }

        return command;
    }

    private RestaurantSubCommand getSubCommand(String options) {
        return RestaurantSubCommand.of(StringUtils.substringBefore(options, " "));
    }
}
