package com.example.auth_service.request;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String role;
    private String password;
    private String email;
}
