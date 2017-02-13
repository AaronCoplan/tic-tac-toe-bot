package com.tictactoebot.dataHandler.error;

/**
 * Created by afcoplan on 2/12/17.
 */
public class IllegalMoveException extends Exception {

    public IllegalMoveException(){
        super("Cannot go there!  Spot already in play.");
    }
}
