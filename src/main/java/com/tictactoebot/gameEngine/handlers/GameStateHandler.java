package com.tictactoebot.gameEngine.handlers;

import com.tictactoebot.UI.DrawMove;
import com.tictactoebot.UI.Frame;
import com.tictactoebot.UI.Position;
import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Devin on 2/11/2017.
 */
public class GameStateHandler {

    private static Board board;
    private static Game game;

    private static boolean gameOver;
    private static boolean playerTurn;

    private static Hashtable<Integer, Position> moveLocations;

    private static int winnerNum;

    private static int playerNumber, computerNumber;

    public static boolean isGameOver(){
        return gameOver;
    }

    public static boolean isPlayerTurn(){
        return playerTurn;
    }

    public static int getWinnerNum(){
        return winnerNum;
    }

    public static int getNumMoves(){return game.getNumMoves();}

    public static void init(){
        board = new Board();
        game = new Game();

        gameOver = false;

        playerNumber = Frame.askXO();
        if(playerNumber == -1){
            onGameOver(-1);
        }

        computerNumber  = playerNumber == 0? 1 : 0;
        playerTurn = (playerNumber == 0);

        GameStateHandler.playerNumber = playerNumber;

        moveLocations = GameStateHandler.createMoveLocations();
    }

    public static int getComputerNumber(){
        return computerNumber;
    }

    public static void onUserInput(int x, int y){

        if (x < Frame.cornerCoords[0].x && y < Frame.cornerCoords[0].y){
            doMove(0, playerNumber);
        } else if (x < Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            doMove(1, playerNumber);
        } else if (x > Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            doMove(2, playerNumber);
        } else if (x < Frame.cornerCoords[2].x && y < Frame.cornerCoords[2].y) {
            doMove(3, playerNumber);
        } else if (x < Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            doMove(4, playerNumber);
        } else if (x > Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            doMove(5, playerNumber);
        } else if (x < Frame.cornerCoords[2].x && y > Frame.cornerCoords[3].y){
            doMove(6, playerNumber);
        } else if (x < Frame.cornerCoords[3].x && y > Frame.cornerCoords[3].y){
            doMove(7, playerNumber);
        } else {
            doMove(8, playerNumber);
        }
    }

    public static Board getBoard(){
        return board;
    }

    public static Hashtable<Integer, Position> createMoveLocations(){
        Position[] cornerCoords = Frame.getCornerCoords();

        int distance = cornerCoords[1].x - cornerCoords[0].x;
        int midPoint = distance/2;

        //Locations are laid out like this:
        //0, 1, 2
        //3, 4, 5
        //6, 7, 8

        Hashtable<Integer, Position> moveLocations = new Hashtable<Integer, Position>();

        moveLocations.put(0, new Position(cornerCoords[0].x - midPoint, cornerCoords[0].y - midPoint)); //Locations of where to put the X's and O's.
        moveLocations.put(1, new Position(cornerCoords[1].x - midPoint, cornerCoords[0].y - midPoint));
        moveLocations.put(2, new Position(cornerCoords[1].x + midPoint, cornerCoords[0].y - midPoint));
        moveLocations.put(3, new Position(cornerCoords[0].x - midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(4, new Position(cornerCoords[1].x - midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(5, new Position(cornerCoords[1].x + midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(6, new Position(cornerCoords[0].x - midPoint, cornerCoords[2].y + midPoint));
        moveLocations.put(7, new Position(cornerCoords[1].x - midPoint, cornerCoords[2].y + midPoint));
        moveLocations.put(8, new Position(cornerCoords[1].x + midPoint, cornerCoords[2].y + midPoint));

        return moveLocations;
    }


    public static boolean doComputerMove(int location){
        return doMove(location, computerNumber);
    }

    public static void setPlayerTurn(){
        playerTurn = true;
    }

    private static boolean doMove(int location, int playerNum){ //player num is 0 if X, 1 if O
        if (board.getChar(location) != '-')
            return false;
        if (playerNum == 0) {
            try {
                board.setChar(location, 'X');
            } catch (IllegalMoveException e){
                e.printStackTrace();
            }

            try {
                new DrawMove('x', moveLocations.get(location));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                board.setChar(location, 'O');
            } catch (IllegalMoveException e){
                e.printStackTrace();
            }

            try {
                new DrawMove('o', moveLocations.get(location));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        game.addMove(board, location);

        System.out.println(board);

        if(game.getNumMoves() >= 5)
            GameStateHandler.checkForWin();

        if(!gameOver){
            playerTurn = false;
        }

        return true;
    }


    /*public static void restartGame(){
        if(!noGUI) {
            Frame.recreatePanel();
        }

        startNewGame();
    }*/

    public static Game getGame(){
        return game;
    }

    public static void checkForWin(){
        int result;

        for (int i = 0; i < 3; i++){
            result = isEqual(3*i, 3*i+1, 3*i+2);
            if (result != -1){
                onGameOver(result);
                return;
            }
        }

        for (int j = 0; j < 3; j++){
            result = isEqual(j, j+3, j+6);
            if(result != -1){
                onGameOver(result);
                return;
            }
        }

        if ((result = isEqual(0, 4, 8)) != -1){
            onGameOver(result);
            return;
        } else if ((result = isEqual(2, 4, 6)) != -1){
            onGameOver(result);
            return;
        }

        if(game.getNumMoves() == 9){
            onGameOver(-1);
        }
    }

    private static int isEqual(int i1, int i2, int i3){
        if (board.getChar(i1) == board.getChar(i2) && board.getChar(i2) == board.getChar(i3)){
            if(board.getChar(i1) == 'X'){
                return 0;
            } else if (board.getChar(i1) == 'O'){
                return 1;
            }
        }
        return -1;
    }

    private static void onGameOver(int playerNum){  //0 for X, 1 for O victory, -1 for tie.
        if(playerNum == -1){
            System.out.println("Tie!");
            game.setResult('T');
        } else {
            char result = playerNum == 0 ? 'X' : 'O';
            game.setResult(result);
            System.out.println("Player: " + result + " wins!");
        }

        winnerNum = playerNum;
        gameOver = true;

        //dataHandler.saveGame(game);
        Frame.repaint();
    }

    public static Game getGame(){
        return game;
    }
}