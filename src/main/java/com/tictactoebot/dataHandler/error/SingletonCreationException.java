package com.tictactoebot.dataHandler.error;

/**
 * Created by afcoplan on 2/13/17.
 */
public class SingletonCreationException extends Exception {
    public SingletonCreationException(){
        super("Error instantiating more than one data handler!  This is not allowed.");
    }
}
