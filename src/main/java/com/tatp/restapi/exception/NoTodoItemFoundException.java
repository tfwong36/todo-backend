package com.tatp.restapi.exception;

public class NoTodoItemFoundException extends RuntimeException  {
    public NoTodoItemFoundException() {
        super("No Todo Item Found");
    }
}
