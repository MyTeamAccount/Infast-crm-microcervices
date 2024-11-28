package com.example.project_service.exeption;

public class ApiException extends RuntimeException
{
    public ApiException(String message)
    {
        super(message);
    }
}
