package com.irostub.telegramtapbot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private LocalDate birthday;

    public static Account create(Long id,String firstName, String lastName, String username) {
        Account account = new Account();
        account.id = id;
        account.firstName = firstName;
        account.lastName = lastName;
        account.username = username;
        return account;
    }

    public String getName(){
        return (StringUtils.isNotEmpty(lastName) ? lastName : "") + firstName;
    }
}
