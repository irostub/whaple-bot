package com.irostub.telegramtapbot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatChannelUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatChannelId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatChannel chatChannel;
}
