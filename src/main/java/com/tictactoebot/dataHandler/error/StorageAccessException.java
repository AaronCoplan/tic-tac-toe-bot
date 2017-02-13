package com.tictactoebot.dataHandler.error;

/**
 * Created by afcoplan on 2/12/17.
 */
public class StorageAccessException extends Exception {

    public StorageAccessException(){}

    public StorageAccessException(String message){
        super(message);
    }
}
