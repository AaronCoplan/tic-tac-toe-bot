package com.tictactoebot.trainer;

import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by afcoplan on 2/14/17.
 */
public class RandomTrainer {

    public static void move(){

        boolean moveSuccess = false;

        while(!moveSuccess){

            int location = (int)(Math.random() * 9);

            moveSuccess = GameStateHandler.doComputerMove(location);
        }
    }
}
