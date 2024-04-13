package com.example.Messenger;

public class Chat {
    private String username;
    private String chatid;
    private String type;

    public Chat(String username, String chatid, String type) {
        this.username = username;
        this.chatid = chatid;
        this.type = type;
    }

    public Chat(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
