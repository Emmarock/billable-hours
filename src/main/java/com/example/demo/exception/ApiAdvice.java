package com.example.demo.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiAdvice {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleIOException(IOException e) {
        HashMap<String, String> response = new HashMap<>();
        response.put("error",e.getLocalizedMessage());
        return response;
    }
    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleUnrecognizedPropertyException(UnrecognizedPropertyException e){
        HashMap<String, String> response = new HashMap<>();
        response.put("Unrecognized field",e.getPropertyName());
        response.put("Recognized fields", e.getKnownPropertyIds().toString());
        response.put("Message","Ensure the CSV Header comply with the Recognized fields");
        return response;
    }

    @ExceptionHandler(TimeTableRecordException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String,String> handleTImeTableRecordException(TimeTableRecordException ex){
        HashMap<String, String> response = new HashMap<>();
        response.put("",ex.getLocalizedMessage());
        return response;
    }
}
