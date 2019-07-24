package com.example.demo.exception;

public class TimeTableRecordException extends RuntimeException {
    public TimeTableRecordException() {
    }

    public TimeTableRecordException(String message) {
        super(message);
    }
}
