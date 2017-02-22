package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Utils;
import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;

import java.io.*;
import java.net.Socket;

public class ClientEngine {
    private Socket host;

    private DataHandler dataHandler;

    private final char[] letters = {'X', 'O'};



    public ClientEngine(String ip, int port, int numGames, DataHandler dataHandler){

        this.dataHandler = dataHandler;

        try {
            host = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            gameLoop(numGames);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Exception: Could not create input/ output streams!");
        }
    }

    private void gameLoop(int numGames) throws IOException {
        System.out.println("num games: " + numGames);

        BufferedReader in = new BufferedReader(new InputStreamReader(host.getInputStream()));
        PrintWriter out = new PrintWriter(host.getOutputStream(), true);


        //Sleep to make sure writers and readers are set up before reading/writing
        Utils.sleep(250);

        int computerWinCount = 0;
        int computerTieCount = 0;

        int numRandomMoves = 0;
        int numSmartMoves = 0;

        int errorCount = 0;


        for (int i = 0; i < numGames; i++){

            //For the server to know when the client will disconnect (eventually prints end).
            out.println("continue");

            //read in which letter the client will be
            int thisLetterIndex = Integer.parseInt(in.readLine());
            System.out.println("this letter index: " + thisLetterIndex);


            ComputeEngine computeEngine = new ComputeEngine(letters[thisLetterIndex], dataHandler);
            char hostLetter = letters[thisLetterIndex == 0? 1 : 0];


            Game game = new Game();
            Board board = new Board();

            boolean clientTurn = thisLetterIndex == 0;

            int gameIsOver = Board.NOT_OVER;

            while(gameIsOver == Board.NOT_OVER){
                if(clientTurn){
                    int clientMove = computeEngine.getMove(board);

                    try {
                        board.setChar(clientMove, computeEngine.getLetter());
                        game.addMove(board, clientMove);

                        //Tell the client where the computer went.
                        out.println(clientMove);
                    } catch (IllegalMoveException e) {
                        e.printStackTrace();
                    }
                } else { //Client's turn
                    //Read the client's move index.
                    int serverMove = Integer.parseInt(in.readLine());

                    try {
                        board.setChar(serverMove, hostLetter);
                        game.addMove(board, serverMove);
                    } catch (IllegalMoveException e) {
                        e.printStackTrace();
                    }
                }

                //Check if game is over yet

                if (game.getNumMoves() >= 5){
                    gameIsOver = board.checkResult();
                }

                board.prettyPrint();

                //Switch turns

                clientTurn = !clientTurn;

            }

            //Game is over

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

            //Tell the server to continue to play games
        }

        //Tells the server that the client is going to disconnect.
        out.println("end");

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

    }
}