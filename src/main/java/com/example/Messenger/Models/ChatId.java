package com.example.Messenger.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

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

    public ChatId(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getChatid() {
        return chatid;
    }

    public void setChatid(UUID chatid) {
        this.chatid = chatid;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }
}
