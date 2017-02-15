package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;
import com.tictactoebot.trainer.RandomTrainer;

/**
 * Created by afcoplan on 2/13/17.
 */
public class HumanVsBotEngine {
    private ComputeEngine computeEngine;

    // TODO: this should take a constructor of the compute engine
    public HumanVsBotEngine(ComputeEngine engine){
        computeEngine = engine;
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
                //computeEngine.move(GameStateHandler.get
                RandomTrainer.move();
                GameStateHandler.setPlayerTurn();
            }

            try{
                Thread.sleep(10);
            }catch(Exception e){}
        }

    }




}
