/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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

            //RandomTrainer randomTrainer = new RandomTrainer(letters[trainerLetterIndex]);

            //ComputeEngine randomTrainer = new ComputeEngine(letters[trainerLetterIndex], dataHandler);
            //^ uncomment this line and comment out line below to get ComputeEngine VS. ComputeEngine.

            RandomTrainer randomTrainer = new RandomTrainer(letters[trainerLetterIndex]);
            ComputeEngine computeEngine = new ComputeEngine(letters[computerLetterIndex], dataHandler);

            Game game = new Game();
            Board board = new Board();

            // now that the game is setup, play it

            // randomly determine who goes first so it's a good mix in data

            boolean computerTurn = computerLetterIndex == 0;
            //^ recomment this line and uncomment out line below to switch who goes first.

            //boolean computerTurn = ((int)(Math.random() * 2)) == 0;

            int gameIsOver = Board.NOT_OVER;

            while(gameIsOver == Board.NOT_OVER){
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

                if(game.getNumMoves() >= 5){
                    gameIsOver = board.checkResult();
                }

                computerTurn = !computerTurn;

                //board.prettyPrint();
                //System.out.println();
            }

            System.out.print("Game " + i + " played, RESULT = ");

            numRandomMoves += computeEngine.getNumRandomMovesChosen();
            numSmartMoves += computeEngine.getNumSmartMovesChosen();

            char result = '-'; // default val

            switch(gameIsOver)  //Changed from board.checkResult()
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
            dataHandler.saveGame(game, computeEngine.getLetter());

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
