package com.tictactoebot.computeEngine;

import com.tictactoebot.dataHandler.model.Board;

/**
 * Created by Devin on 2/14/2017.
 */
public class ComputeEngine {

    private final char letter;

    public ComputeEngine(char letter){
        this.letter = letter;
    }

    public int getMove(Board board){
        int location;

        do {
            location = (int)(Math.random() * 9);
        } while(board.isOccupied(location));

        return location;
    }
}
