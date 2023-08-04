package com.example.blogapi.Exceptions;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{
    private HttpStatus status;
    private String message;
    public BlogApiException(HttpStatus status,String message){
        super(message);
        this.message=message;
        this.status=status;
    }
    public HttpStatus getStatus(){
        return status;
    }
}
