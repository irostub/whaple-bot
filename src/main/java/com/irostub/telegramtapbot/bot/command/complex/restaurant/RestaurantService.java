package com.irostub.telegramtapbot.bot.command.complex.restaurant;

import com.irostub.telegramtapbot.bot.command.complex.*;
import com.irostub.telegramtapbot.bot.command.utils.ParseUtils;
import com.irostub.telegramtapbot.domain.Account;
import com.irostub.telegramtapbot.domain.IgnoreRestaurant;
import com.irostub.telegramtapbot.domain.Restaurant;
import com.irostub.telegramtapbot.repository.AccountRepository;
import com.irostub.telegramtapbot.repository.IgnoreRestaurantRepository;
import com.irostub.telegramtapbot.repository.RestaurantRepository;
import com.irostub.telegramtapbot.repository.query.IgnoreRestaurantQueryRepository;
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
    private final IgnoreRestaurantRepository ignoreRestaurantRepository;
    private final IgnoreRestaurantQueryRepository ignoreRestaurantQueryRepository;

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
            case IGNORE:
                ignore(subOptions, pack);
                break;
            case RESTORE:
                restore(subOptions, pack);
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
        sendExecute(pack, sendMessage);
    }

    private void create(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Optional<Restaurant> optionalRestaurant = repository.findByName(restaurantName);

        if (optionalRestaurant.isPresent()) {
            SendMessage sendMessage = createExistsRestaurantMessage(pack, restaurantName);

            sendExecute(pack, sendMessage);
        } else {
            Account account = getAccount(pack);
            Restaurant restaurant = Restaurant.newRestaurant(restaurantName, null, null, account);
            repository.save(restaurant);

            SendMessage sendMessage = createRestaurantMessage(pack, restaurantName);

            sendExecute(pack, sendMessage);
        }
    }

    private void delete(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Restaurant restaurant = getRestaurant(restaurantName);
        SendMessage message;

        if (isDeleteOwner(pack, restaurant) == false) {
            message = deleteNotOwnerRestaurantMessage(pack, restaurant.getAccount().getName(), restaurantName);
        } else {
            message = deleteRestaurantMessage(pack, restaurantName);
            repository.delete(restaurant);
        }

        sendExecute(pack, message);
    }

    private void ignore(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Account account = getAccount(pack);
        Restaurant restaurant = getRestaurant(restaurantName);

        Optional<IgnoreRestaurant> exist = ignoreRestaurantRepository.findByAccountAndRestaurant(account, restaurant);
        SendMessage message;

        if (exist.isPresent()) {
            message = ignoreRestaurantExistMessage(pack, account.getName(), restaurantName);
        } else {
            IgnoreRestaurant ignoreRestaurant = new IgnoreRestaurant(account, restaurant);
            ignoreRestaurantRepository.save(ignoreRestaurant);
            message = successIgnoreRestuarantMessage(pack, restaurantName);
        }

        sendExecute(pack, message);
    }

    private void restore(String subOptions, CommandGatewayPack pack) {
        String restaurantName = subOptions.strip();

        if (isWrongUsage(pack, restaurantName)) return;

        Account account = getAccount(pack);
        Restaurant restaurant = getRestaurant(restaurantName);

        Optional<IgnoreRestaurant> ignoreRestaurant = ignoreRestaurantRepository.findByAccountAndRestaurant(account, restaurant);
        SendMessage message;
        if (ignoreRestaurant.isPresent()) {
            ignoreRestaurantRepository.delete(ignoreRestaurant.get());
            message = successRestoreIgnoreRestaurantMessage(pack, restaurantName);
        } else {
            message = notExistIgnoreRestaurantMessage(pack, restaurantName);
        }

        sendExecute(pack, message);
    }

    private boolean isDeleteOwner(CommandGatewayPack pack, Restaurant findRestaurant) {
        return findRestaurant.getCreatedBy().equals(pack.getUpdate().getMessage().getFrom().getId().toString());
    }

    private void read(CommandGatewayPack pack) {
        String restaurantNameList = repository.findAll().stream()
                .map(Restaurant::getName)
                .collect(Collectors.joining("\n"));

        SendMessage sendMessage = readRestaurantListMessage(pack, restaurantNameList);

        sendExecute(pack, sendMessage);
    }

    private void recommend(String subOptions, CommandGatewayPack pack) {
        Integer limit = Integer.parseInt(subOptions.strip());
        List<Restaurant> pickedRestaurants = repository.findByRandom(limit);

        Account account = getAccount(pack);

        pickedRestaurants = removeIgnoreRestaurant(account, pickedRestaurants);
        if (pickedRestaurants.isEmpty()) {
            recommend(subOptions, pack);
        }

        String flatList = pickedRestaurants.stream()
                .map(restaurant -> restaurant.getName() + " - " +
                        restaurant.getAccount().getName() +
                        "님이 추천해준 메뉴에요!\n" + restaurant.getUrl())
                .collect(Collectors.joining("\n"));

        SendMessage sendMessage = recommendMessage(pack, flatList);

        sendExecute(pack, sendMessage);
    }

    private Account getAccount(CommandGatewayPack pack) {
        Account account = accountRepository
                .findById(pack.getUpdate().getMessage().getFrom().getId())
                .orElseThrow(AccountNotFoundException::new);
        return account;
    }

    private Restaurant getRestaurant(String restaurantName) {
        Restaurant restaurant = repository.findByName(restaurantName).orElseThrow(RestaurantNotFoundException::new);
        return restaurant;
    }

    private void sendExecute(CommandGatewayPack pack, SendMessage sendMessage) {
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
        }else if(ParseUtils.canParseInteger(command) == false){
            if(command.split(" ").length == 1){
                RestaurantSubCommand subCommand = getSubCommand(command);
                if(subCommand.equals(RestaurantSubCommand.RECOMMEND)){
                    return subCommand.getProperties().get(0) + " 1";
                } else if (subCommand.equals(RestaurantSubCommand.HELP)) {
                    return command;
                }
            }
        }
        return command;
    }

    private RestaurantSubCommand getSubCommand(String options) {
        return RestaurantSubCommand.of(StringUtils.substringBefore(options, " "));
    }

    private List<Restaurant> removeIgnoreRestaurant(Account account, List<Restaurant> list) {
        List<Long> ignoreList = ignoreRestaurantQueryRepository.getIgnoreList(account).stream()
                .map(IgnoreRestaurant::getRestaurant)
                .map(Restaurant::getId).collect(Collectors.toList());
        List<Restaurant> result = list.stream().filter(attr -> !ignoreList.contains(attr.getId())).collect(Collectors.toList());
        return result;
    }
}
