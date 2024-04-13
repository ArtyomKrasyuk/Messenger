package com.example.Messenger;


public class ChatMessage {
    private String userid;
    private String chatid;
    private String content;
    private String date;
    private String type;

    public ChatMessage(String userid, String chatid, String content, String date, String type) {
        this.userid = userid;
        this.chatid = chatid;
        this.content = content;
        this.date = date;
        this.type = type;
    }

    public ChatMessage(){}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
