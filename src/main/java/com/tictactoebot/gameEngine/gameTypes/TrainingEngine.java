package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.trainer.RandomTrainer;

/**
 * Created by afcoplan on 2/13/17.
 */
public class TrainingEngine {

    public static void train(int numGames, DataHandler dataHandler){

        final char[] letters = {'X', 'O'};

        int computerWinCount = 0;
        int computerTieCount = 0;

        int numRandomMoves = 0;
        int numSmartMoves = 0;

        int errorCount = 0;

        // play half the games as X and half as O
        for(int i = 0; i < numGames; ++i){

            // setup the game

            int computerLetterIndex = i % 2;
            int trainerLetterIndex = (computerLetterIndex == 0) ? 1 : 0;

            System.out.println("Game: " + i);
            System.out.println("Computer Letter: " + letters[computerLetterIndex]);
            System.out.println("Trainer Letter: " + letters[trainerLetterIndex]);

            RandomTrainer randomTrainer = new RandomTrainer(letters[trainerLetterIndex]);
            ComputeEngine computeEngine = new ComputeEngine(letters[computerLetterIndex], dataHandler);

            Game game = new Game();
            Board board = new Board();

            // now that the game is setup, play it

            // randomly determine who goes first so it's a good mix in data
            boolean computerTurn = ((int)(Math.random() * 2)) == 0;

            while(board.checkResult() == Board.NOT_OVER){
                if(computerTurn){
                    //computer goes
                    int moveIndex = computeEngine.getMove(board);
                    System.out.println("Computer Choice: " + moveIndex);
                    try{
                        board.setChar(moveIndex, computeEngine.getLetter());
                        game.addMove(board, moveIndex);
                    }catch(IllegalMoveException e){
                        e.printStackTrace();
                    }
                }else{
                    //trainer goes
                    int moveIndex = randomTrainer.getMove(board);
                    System.out.println("Trainer Choice: " + moveIndex);
                    try{
                        board.setChar(moveIndex, randomTrainer.getLetter());
                        game.addMove(board, moveIndex);
                    }catch(IllegalMoveException e){
                        e.printStackTrace();
                    }
                }

                computerTurn = !computerTurn;

                //board.prettyPrint();
                //System.out.println();
            }

            System.out.print("Game " + i + " played, RESULT = ");

            numRandomMoves += computeEngine.getNumRandomMovesChosen();
            numSmartMoves += computeEngine.getNumSmartMovesChosen();

            char result = '-'; // default val

            switch(board.checkResult())
            {
                case Board.TIE:     System.out.println("TIE");
                                    result = 'T';
                                    ++computerTieCount;
                                    break;

                case Board.X_WINS:  System.out.println("X");
                                    result = 'X';
                                    if(computeEngine.getLetter() == 'X') ++computerWinCount;
                                    break;

                case Board.O_WINS:  System.out.println("O");
                                    result = 'O';
                                    if(computeEngine.getLetter() == 'O') ++computerWinCount;
                                    break;

                default:            System.out.println("\nERROR! SOMETHING WENT WRONG!");
                                    ++errorCount;
                                    break;
            }

            // save the game
            game.setResult(result);
            dataHandler.saveGame(game);

        }

        System.out.println("\n");
        System.out.println("Number of Games: " + numGames);
        System.out.println("Number of Computer Wins: " + computerWinCount);
        System.out.println("Number of Computer Ties: " + computerTieCount);

        double winRate = (double)(computerWinCount) / (double)(numGames);
        double tieRate = (double)(computerTieCount) / (double)(numGames);

        System.out.println("Percent Games Won: " + (winRate * 100));
        System.out.println("Percent Games Tied: " + (tieRate * 100));
        System.out.println("Percent Games Won or Tied: " + ((tieRate + winRate) * 100));

        System.out.println("\nError Count: " + errorCount);

        System.out.println("\nNum Smart Moves Chosen: " + numSmartMoves);
        System.out.println("Num Random Moves Chosen: " + numRandomMoves);

        /*
         *  Would also be cool to track computer wins and/or ties as a function of number of games played.
         */



    }
}
