package com.tictactoebot.trainer;

import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;

/**
 * Created by afcoplan on 2/14/17.
 */
public class RandomTrainer {

    private final char letter;

    public RandomTrainer(char letter){
        this.letter = letter;
    }

    public int getMove(Board board){

        int location = -1;

        boolean moveSuccess = false;

        while(!moveSuccess){

            location = (int)(Math.random() * 9);

            try{
                board.setChar(location, letter);
                moveSuccess = true;
            }catch(IllegalMoveException e){
                moveSuccess = false;
            }
        }

        return location;
    }
}
