package com.tictactoebot.dataHandler.error;

/**
 * Created by afcoplan on 2/12/17.
 */
public class MoveAlreadyWrittenException extends Exception {

    public MoveAlreadyWrittenException(){}

    public MoveAlreadyWrittenException(int gameNumber, int moveNumber){
        super("Move G" + gameNumber + ", M" + moveNumber + " already exists!");
    }
}
