package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.gameEngine.handlers.GameOverHandler;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by afcoplan on 2/13/17.
 */
public class HumanVsBotEngine {

    private boolean gameLoopReturned;

    // TODO: this should take a constructor of the compute engine
    public HumanVsBotEngine(){
        this.gameLoopReturned = false;
    }

    public void playGame(){

        GameStateHandler.init();

        Frame.createFrame();

        new Thread(new GameOverHandler()).start();

        gameLoop();
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

        gameLoopReturned = true;

        System.out.println("GAME LOOP ENDED");
    }

    public void doComputerMove(){

        boolean moveSuccess = false;

        while(!moveSuccess){

            int location = (int)(Math.random() * 9);

            moveSuccess = GameStateHandler.doComputerMove(location);
        }
    }


}
