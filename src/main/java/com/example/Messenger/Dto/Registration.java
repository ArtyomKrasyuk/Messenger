package com.example.Messenger.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
    private String username;
    private String phone;
    private String password1;
    private String password2;
}
