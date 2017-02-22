package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Utils;
import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HostEngine {
   private ServerSocket srv;
   private Socket client;

   private DataHandler dataHandler;

   private final char[] letters = {'X', 'O'};



   public HostEngine(int port, DataHandler dataHandler){

      try {
         srv = new ServerSocket(port);
      } catch (IOException e) {
         e.printStackTrace();
      }

      this.dataHandler = dataHandler;

      while(true) {  //Continues to try to connect to clients after the game ends
         try {
            client = srv.accept(); //Wait for the client to connect and create a socket
            System.out.println("Connected to client!");
         } catch (IOException e) {
            e.printStackTrace();
         }

         try {
            gameLoop();
         } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception: Could not create input/ output streams!");
         }
      }
   }

   private void gameLoop() throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      PrintWriter out = new PrintWriter(client.getOutputStream(), true);

      System.out.println("Streams created");

      //Sleep to make sure writers and readers are set up before reading/writing
      Utils.sleep(250);

      int computerWinCount = 0;
      int computerTieCount = 0;

      int numRandomMoves = 0;
      int numSmartMoves = 0;

      int errorCount = 0;

      int numGames = 0;

      //Clears first continue.
      System.out.println(in.readLine());

      while(true){
         numGames++;  //Counts how many games have been played
         //Decide who goes first
         int thisLetterIndex = ((int)(Math.random() * 2));
         int clientLetterIndex = (thisLetterIndex == 0) ? 1 : 0;

         //Tell the client which letter it will be.
         out.println(clientLetterIndex);

         ComputeEngine computeEngine = new ComputeEngine(letters[thisLetterIndex], dataHandler);

         Game game = new Game();
         Board board = new Board();

         boolean serverTurn = thisLetterIndex == 0;

         int gameIsOver = Board.NOT_OVER;

         while(gameIsOver == Board.NOT_OVER){
            if(serverTurn){
               int serverMove = computeEngine.getMove(board);

               try {
                  board.setChar(serverMove, computeEngine.getLetter());
                  game.addMove(board, serverMove);

                  //Tell the client where the computer went.
                  out.println(serverMove);
               } catch (IllegalMoveException e) {
                  e.printStackTrace();
               }
            } else { //Client's turn
               //Read the client's move index.
               int clientMove = Integer.parseInt(in.readLine());

               try {
                  board.setChar(clientMove, letters[clientLetterIndex]);
                  game.addMove(board, clientMove);
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

            serverTurn = !serverTurn;

         }

         //Game is over

         System.out.print("Game " + numGames + " played, RESULT = ");

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

         if (in.readLine().equals("end")){
            break;
         }

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

   }
}