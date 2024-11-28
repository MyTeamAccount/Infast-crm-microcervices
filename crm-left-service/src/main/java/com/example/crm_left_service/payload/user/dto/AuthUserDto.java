package com.example.crm_left_service.payload.user.dto;

import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private String role;
}