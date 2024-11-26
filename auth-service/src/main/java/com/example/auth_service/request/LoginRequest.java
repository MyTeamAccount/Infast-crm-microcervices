package com.example.auth_service.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
