package com.example.crm_left_service.request;


import com.example.crm_left_service.entity.UserDetails;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Id is required")
    private Long id;
    private String username;
    private String password;
    private UserDetails userDetails;
}
