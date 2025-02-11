package ru.hogwarts.schoolloohcs.Exceptions;

public class DBisEmptyException extends RuntimeException{

    public DBisEmptyException(String message){
        super(message);
    }
}
