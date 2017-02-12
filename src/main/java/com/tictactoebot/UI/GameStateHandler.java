package com.tictactoebot.UI;

import java.io.IOException;
import java.util.Hashtable;

import static com.tictactoebot.UI.Frame.*;
import static com.tictactoebot.UI.Utils.*;

/**
 * Created by Devin on 2/11/2017.
 */
public class GameStateHandler {
    public static boolean playerTurn = true;
    private static int playerNum = 0;   //These are 0 if that player is X, 1 if that player is O
    private static int computerNum = 0;
    public static char[] boardState = new char[9];
    public static int numMoves = 0;
    public static boolean gameOver = false;
    private static Thread gameLoop;
    private static boolean randomTrainerOn = false;
    private static boolean noGUI = false;
    private static boolean resetOnGameEnd = false;
    private static Hashtable<Integer, Position> moveLocations = new Hashtable<>();


    private GameStateHandler(){
    }

    public static void createGame(boolean randomTrainerOn, boolean resetOnGameEnd, boolean noGUI){
        GameStateHandler.randomTrainerOn = randomTrainerOn;
        GameStateHandler.resetOnGameEnd = resetOnGameEnd;
        GameStateHandler.noGUI = noGUI;

        if(!noGUI) {
            createFrame();
        }

        createMoveLocations();

        startNewGame();
    }

    private static void startNewGame(){
        gameOver = false;
        numMoves = 0;

        for (int i = 0; i < 9; i++){
            boardState[i] = '_';
        }

        if((int)(2 * Math.random() + 1) == 1){
            playerTurn = true;
            playerNum = 0;
            computerNum = 1;
        } else {
            playerTurn = false;
            playerNum = 1;
            computerNum = 0;
        }

        gameLoop = new Thread(new GameLoop());
        gameLoop.start();
    }

    private static void createMoveLocations(){
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
    }


    public static void restartGame(){
        Thread t = new Thread(new GameRestarter());
        t.start();
    }

    private static boolean doMove(int location, int playerNum){ //player num is 0 if X, 1 if O
        if (boardState[location] != '_')
            return false;
        //TODO: Store move info after move is completed.
        if (playerNum == 0) {
            boardState[location] = 'x';
            if(!noGUI) {
                try {
                    new DrawMove('x', moveLocations.get(location));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            boardState[location] = 'o';
            if(!noGUI) {
                try {
                    new DrawMove('o', moveLocations.get(location));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        printBoardState();

        if(++numMoves >= 5)
            checkForWin();

        if(!gameOver){
            if (playerTurn) {
                playerTurn = false;
            } else {
                playerTurn = true;
            }
        }

        return true;
    }

    public static void printBoardState(){
        for(int i = 0 ; i < boardState.length; i += 3){
            System.out.println(boardState[i] + " | " + boardState[i + 1] + " | " + boardState[i + 2]);
        }
    }

    public static void onUserInput(int x, int y){

        if (x < Frame.cornerCoords[0].x && y < Frame.cornerCoords[0].y){
            doMove(0, playerNum);
        } else if (x < Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            doMove(1, playerNum);
        } else if (x > Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            doMove(2, playerNum);
        } else if (x < Frame.cornerCoords[2].x && y < Frame.cornerCoords[2].y) {
            doMove(3, playerNum);
        } else if (x < Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            doMove(4, playerNum);
        } else if (x > Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            doMove(5, playerNum);
        } else if (x < Frame.cornerCoords[2].x && y > Frame.cornerCoords[3].y){
            doMove(6, playerNum);
        } else if (x < Frame.cornerCoords[3].x && y > Frame.cornerCoords[3].y){
            doMove(7, playerNum);
        } else {
            doMove(8, playerNum);
        }
     }

    public static void randomComputerMove() {
        int location = (int) (9 * Math.random());

        while (!doMove(location, computerNum) && !gameOver) {
            location = (int) (9 * Math.random());
        }
    }

    public static void realComputerMove(){
        //Call Andrew/Aaron's function which will give a location back (0 - 8)

        //if(!gameOver){
        //  int location = callFunction();
        //  doMove(location, computerNum)
        //}
        //playerTurn = true;
    }

    private static void checkForWin(){
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

        if(numMoves == 9){
            onGameOver(-1);
        }
    }

    private static int isEqual(int i1, int i2, int i3){
        if (boardState[i1] == boardState[i2] && boardState[i2] == boardState[i3]){
            if(boardState[i1] == 'x'){
                return 0;
            } else if (boardState[i1] == 'o'){
                return 1;
            }
        }
        return -1;
    }

    private static void onGameOver(int playerNum){  //0 for X, 1 for O victory, -1 for tie.
        if(playerNum == -1){
            System.out.println("Tie!");
        } else {
            System.out.println("Player: " + (playerNum == 0 ? 'X' : 'O') + " wins!");
        }
        gameOver = true;

        if(resetOnGameEnd) {
            restartGame();
        }
    }

    public static void trainerMove(int location){  //Call this if you want the trainer to move to a specific location.
        if(doMove(location, playerNum)){
            playerTurn = false;
        }
    }

    private static void randomTrainerMove(){
        int location = (int)(9 * Math.random());

        while (!doMove(location, playerNum) && !gameOver) {
            location = (int) (9 * Math.random());
        }
    }

    static class GameLoop implements Runnable{
        public void run(){
            while(!gameOver){
                if(playerTurn){
                    if(randomTrainerOn)
                        randomTrainerMove();
                } else {
                    randomComputerMove();
                }

                sleep(1);
            }
        }
    }

    static class GameRestarter implements Runnable{
        public void run(){
            join(gameLoop);

            if(!noGUI) {
                recreatePanel();
            }

            startNewGame();
        }
    }

}
