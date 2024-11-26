package com.example.auth_service.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String email;
    private String password;
    private String role;
}
