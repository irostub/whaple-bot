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
public class ChatGroup {
    @Id
    private String chatGroupId;

    private String chatGroupName;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatGroupUser> chatGroupUser = new ArrayList<>();
}
