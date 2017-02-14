package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by afcoplan on 2/13/17.
 */
public class HumanVsBotEngine {

    // TODO: this should take a constructor of the compute engine
    public HumanVsBotEngine(){
    }

    public void playGame(){

        GameStateHandler.init();

        Frame.createFrame();

        gameLoop();

        System.out.println("Game over");
    }

    private void gameLoop(){
        while(!GameStateHandler.isGameOver()){
            if(!GameStateHandler.isPlayerTurn()){
                // TODO: get the move from the compute engine
                doComputerMove();
                GameStateHandler.setPlayerTurn();
            }

            try{
                Thread.sleep(10);
            }catch(Exception e){}
        }

    }

    public void doComputerMove(){

        boolean moveSuccess = false;

        while(!moveSuccess){

            int location = (int)(Math.random() * 9);

            moveSuccess = GameStateHandler.doComputerMove(location);
        }
    }


}
