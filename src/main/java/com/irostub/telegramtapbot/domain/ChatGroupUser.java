package com.irostub.telegramtapbot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ChatGroupUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatGroupUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatGroup chatGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
