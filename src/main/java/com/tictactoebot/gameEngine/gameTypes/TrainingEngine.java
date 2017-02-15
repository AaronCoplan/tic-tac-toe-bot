package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.dataHandler.model.Board;

/**
 * Created by afcoplan on 2/13/17.
 */
public class TrainingEngine {

    public static void train(int numGames){
        Board board = new Board();

        char[] letters = {'X', 'O'};

        for(int i = 0; i < numGames; ++i){
            //RandomTrainer randomTrainer = new RandomTrainer();
            //ComputeEngine computeEngine = new ComputeEngine();
            System.out.println("Game " + i + " played!");
        }

    }
}
