package com.example.crm_left_service.payload.user.dto;

import com.example.crm_left_service.entity.UserDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private UserDetails userDetails;
}
