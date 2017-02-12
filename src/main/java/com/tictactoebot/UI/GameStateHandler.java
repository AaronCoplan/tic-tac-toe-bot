package com.tictactoebot.UI;

import javax.annotation.processing.SupportedSourceVersion;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Hashtable;

import static com.tictactoebot.UI.Frame.*;

/**
 * Created by Devin on 2/11/2017.
 */
public class GameStateHandler {
    public static boolean playerTurn = true;
    private static boolean playerWentFirst = true;    //Char to keep track of whether the user went first or second.
    public static char[] boardState = new char[9];
    public static int numMoves = 0;
    public static boolean gameOver = false;
    private static Hashtable<Integer, Position> moveLocations = new Hashtable<>();


    private GameStateHandler(){
    }

    public static void createGame(){
        createFrame();
        initializeBoard();
    }

    private static void initializeBoard(){
        for (int i = 0; i < 9; i++){
            boardState[i] = '_';
        }

        int distance = cornerCoords[1].x - cornerCoords[0].x;
        int midPoint = distance/2;

        //Locations are laid out like this:
        //0, 1, 2
        //3, 4, 5
        //6, 7, 8

        moveLocations.put(0, new Position(cornerCoords[0].x - midPoint, cornerCoords[0].y - midPoint)); //Locations of where to put the X's and O's.
        moveLocations.put(1, new Position(cornerCoords[1].x - midPoint, cornerCoords[0].y - midPoint));
        moveLocations.put(2, new Position(cornerCoords[1].x + midPoint, cornerCoords[0].y - midPoint));
        moveLocations.put(3, new Position(cornerCoords[0].x - midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(4, new Position(cornerCoords[1].x - midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(5, new Position(cornerCoords[1].x + midPoint, cornerCoords[2].y - midPoint));
        moveLocations.put(6, new Position(cornerCoords[0].x - midPoint, cornerCoords[2].y + midPoint));
        moveLocations.put(7, new Position(cornerCoords[1].x - midPoint, cornerCoords[2].y + midPoint));
        moveLocations.put(8, new Position(cornerCoords[1].x + midPoint, cornerCoords[2].y + midPoint));

        if((int)(2 * Math.random() + 1) == 1){
            playerTurn = true;
            playerWentFirst = true;
        } else {
            playerTurn = false;
            playerWentFirst = false;
            randomComputerMove();
        }
    }


    private static boolean doMove(int location, int playerNum){ //player num is 0 if X, 1 if O
        if (boardState[location] != '_')
            return false;
        //TODO: Store move info after move is completed.
        if (playerNum == 0) {
            boardState[location] = 'x';
            try {
                new DrawMove('x', moveLocations.get(location));
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            boardState[location] = 'o';
            try {
                new DrawMove('o', moveLocations.get(location));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        if (numMoves == 8){
            onGameOver(-1);
        } else if(numMoves++ >= 5 )
            checkForWin();
        printBoardState();

        return true;
    }

    public static void printBoardState(){
        for(int i = 0 ; i < boardState.length; i += 3){
            System.out.println(boardState[i] + " | " + boardState[i + 1] + " | " + boardState[i + 2]);
        }
    }

    public static void onUserInput(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        int playerNum = playerWentFirst? 0 : 1; //if player went first, then there move will be an X, else it will be an O

        if (x < Frame.cornerCoords[0].x && y < Frame.cornerCoords[0].y){
            if(doMove(0, playerNum))
                playerTurn = false;
        } else if (x < Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            if(doMove(1, playerNum))
                playerTurn = false;
        } else if (x > Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            if(doMove(2, playerNum))
                playerTurn = false;
        } else if (x < Frame.cornerCoords[2].x && y < Frame.cornerCoords[2].y) {
            if(doMove(3, playerNum))
                playerTurn = false;
        } else if (x < Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            if(doMove(4, playerNum))
                playerTurn = false;
        } else if (x > Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            if(doMove(5, playerNum))
                playerTurn = false;
        } else if (x < Frame.cornerCoords[2].x && y > Frame.cornerCoords[3].y){
            if(doMove(6, playerNum))
                playerTurn = false;
        } else if (x < Frame.cornerCoords[3].x && y > Frame.cornerCoords[3].y){
            if(doMove(7, playerNum))
                playerTurn = false;
        } else {
            if(doMove(8, playerNum))
                playerTurn = false;
        }

        if(!playerTurn) {
            randomComputerMove();
        }
     }

    public static void randomComputerMove(){
        int location = (int)(8 * Math.random() + 1);
        while(!doMove(location, playerWentFirst? 1 : 0) && !gameOver){
            location = (int)(8 * Math.random() + 1);
        }
        playerTurn = true;
    }

    private static void checkForWin(){
        int result;
        for (int i = 0; i < 6; i += 3){
            result = isEqual(i, i+1, i+2);
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
        } else if ((result = isEqual(2, 4, 6) )!= -1){
            onGameOver(result);
        }


    }

    private static int isEqual(int i1, int i2, int i3){
        if (boardState[i1] == boardState[i2] && boardState[i2] == boardState[i3]){
            return boardState[i1] == 'x' ? 0 : 1;
        }
        return -1;
    }

    private static void onGameOver(int playerNum){  //0 for X, 1 for O victory, -1 for tie.
        //TODO: Store who wins
        if(playerNum == -1){
            System.out.println("Tie!");
        } else {
            System.out.println("Player: " + (playerNum == 0 ? 'X' : 'O') + " wins!");
        }
        gameOver = true;
    }

}
