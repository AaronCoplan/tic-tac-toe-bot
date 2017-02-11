package com.tictactoebot.UI;

import java.awt.event.MouseEvent;

import static com.tictactoebot.UI.Frame.*;

/**
 * Created by Devin on 2/11/2017.
 */
public class GameStateHandler {
    public static boolean playerTurn = true;
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
            new DrawX(50, 50);
            System.out.println("Top Left");
        } else if (x < Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            //Top middle
            System.out.println("Top Middle");
        } else if (x > Frame.cornerCoords[1].x && y < Frame.cornerCoords[0].y){
            //Top Right
            System.out.println("Top Right");
        } else if (x < Frame.cornerCoords[2].x && y < Frame.cornerCoords[2].y) {
            //Middle left
            System.out.println("Middle Left");
        } else if (x < Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            //Middle middle
            System.out.println("Middle middle");
        } else if (x > Frame.cornerCoords[3].x && y < Frame.cornerCoords[3].y){
            //Middle right
            System.out.println("Middle Right");
        } else if (x < Frame.cornerCoords[2].x && y > Frame.cornerCoords[3].y){
            //Bottom Left
            System.out.println("Bottom Left");
        } else if (x < Frame.cornerCoords[3].x && y > Frame.cornerCoords[3].y){
            //Bottom Middle   
            System.out.println("Bottom Middle"); 
        } else {
            //Bottom Right
            System.out.println("Bottom Right");
        }
     }

}
