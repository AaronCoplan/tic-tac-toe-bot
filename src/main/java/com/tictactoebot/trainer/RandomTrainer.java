package com.tictactoebot.trainer;

import com.tictactoebot.dataHandler.model.Board;

/**
 * Created by afcoplan on 2/14/17.
 */
public class RandomTrainer {

    private final char letter;

    public RandomTrainer(char letter){
        this.letter = letter;
    }

    public char getLetter(){
        return this.letter;
    }

    public int getMove(Board board){

        int location;

        do {
            location = (int)(Math.random() * 9);
        } while(board.isOccupied(location));

        return location;
    }
}
