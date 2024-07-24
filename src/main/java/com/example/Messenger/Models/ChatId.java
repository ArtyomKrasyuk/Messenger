package com.example.Messenger.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat_id")
public class ChatId {
    @Id
    @GeneratedValue
    private long id;
    private UUID chatid;
    private UUID userid;

    public ChatId(UUID chatid, UUID userid) {
        this.chatid = chatid;
        this.userid = userid;
    }
}
