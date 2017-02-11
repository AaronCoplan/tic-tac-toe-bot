package com.tictactoebot.UI;

import java.awt.event.MouseEvent;

import static com.tictactoebot.UI.Frame.*;

/**
 * Created by Devin on 2/11/2017.
 */
public class GameStateHandler {
    public static boolean playerTurn;
    public static char[] boardState = new char[9];

    private GameStateHandler(){
    }

    public static void createGame(){
        createFrame();
    }

    public static void onUserInput(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        if (x < Frame.cornerCoords[0].x && y < Frame.cornerCoords[0].y){
            //Top left
        } else if (x < Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            //Top middle
        } else if (x > Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            //Top Right
        } else if (x < Frame.cornerCoords[2].x && y < Frame.cornerCoords[2].y) {
            //Middle right
        } //else if (x < Frame.)
     }

}
