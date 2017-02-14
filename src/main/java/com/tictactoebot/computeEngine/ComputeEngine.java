package com.tictactoebot.computeEngine;

import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Move;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by Devin on 2/14/2017.
 */
public class ComputeEngine {
    public Move move(Board board){
        int location = (int)(Math.random() * 9);

        while(board.isNotOccupied(location)){
            location = (int)(Math.random() * 9);
        }
        return new Move(GameStateHandler.getNumMoves(), board.toString(),location);
    }
}
