package com.example.Messenger.Models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    private UUID userid;
    @Column(unique = true)
    private String phone;
    private String username;
    private String password;

    public User(UUID userid, String phone, String username, String password) {
        this.userid = userid;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public User(){}

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
