package com.irostub.telegramtapbot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatChannel {
    @Id
    private String chatChannelId;

    private String ChatChannelName;

    @OneToMany
    private List<ChatChannelUser> chatChannelUser = new ArrayList<>();
}
