package com.irostub.telegramtapbot.config;

import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.objects.User;

public class UserInfoHolder {
    private static final ThreadLocal<User> userIdHolder = new ThreadLocal<>();

    public void clearUserIdHolder() {
        userIdHolder.remove();
    }

    public User getUser(){
        User user = userIdHolder.get();
        if (user == null) {
            user = createEmptyUserId();
            userIdHolder.set(user);
        }
        return user;
    }

    public void setUser(User user) {
        Assert.notNull(user, "Only non-null String instances are permitted");
        userIdHolder.set(user);
    }

    public User createEmptyUserId(){
        return new User();
    }

}
