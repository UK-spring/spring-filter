package com.example.springfilter.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private final String username;

    private final String password;

    public SignupRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
