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
}
