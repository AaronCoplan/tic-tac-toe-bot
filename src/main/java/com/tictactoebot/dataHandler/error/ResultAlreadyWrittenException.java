package com.tictactoebot.dataHandler.error;

/**
 * Created by afcoplan on 2/13/17.
 */
public class ResultAlreadyWrittenException extends Exception {

    public ResultAlreadyWrittenException(){}

    public ResultAlreadyWrittenException(int gameNumber){
        super("Result for game G" + gameNumber + " already exists!");
    }
}
