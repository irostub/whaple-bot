package com.irostub.telegramtapbot.bot;

import org.springframework.util.Assert;

public class UserInfoHolder {
    private static final ThreadLocal<String> userIdHolder = new ThreadLocal<>();

    public void clearUserIdHolder() {
        userIdHolder.remove();
    }

    public String getUserId(){
        String username = userIdHolder.get();
        if (username == null) {
            username = createEmptyUserId();
            userIdHolder.set(username);
        }
        return username;
    }

    public void setUserId(String username) {
        Assert.notNull(username, "Only non-null String instances are permitted");
        userIdHolder.set(username);
    }

    public String createEmptyUserId(){
        return "";
    }

}
