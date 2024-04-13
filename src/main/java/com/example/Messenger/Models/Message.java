package com.example.Messenger.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "message_list")
public class Message {
    @Id
    @GeneratedValue
    private long id;
    private UUID chatid;
    private UUID userid;
    private String message;
    private String date;

    public Message(UUID chatid, UUID userid, String message, String date) {
        this.chatid = chatid;
        this.userid = userid;
        this.message = message;
        this.date = date;
    }

    public Message(){}

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
