package com.example.blogapi.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    Date timestamp;
    String message;
    String details;

}
