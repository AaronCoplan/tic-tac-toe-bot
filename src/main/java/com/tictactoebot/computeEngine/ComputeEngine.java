package com.tictactoebot.computeEngine;

import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Move;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by Devin on 2/14/2017.
 */
public class ComputeEngine {
    public int move(Board board){
        int location;

        do {
            location = (int)(Math.random() * 9);
        } while(board.isOccupied(location));

        return location;
    }
}
