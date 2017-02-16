package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.UI.Utils;
import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by afcoplan on 2/13/17.
 */
public class HumanVsBotEngine {
    private ComputeEngine computeEngine;

    private DataHandler dataHandler;

    public HumanVsBotEngine(DataHandler dataHandler){
        this.dataHandler = dataHandler;
    }

    public void playGame(){

        GameStateHandler.init();

        this.computeEngine = initComputeEngine();

        Frame.createFrame();

        Utils.sleep(10);

        gameLoop();

        System.out.println("Game over");
    }

    private ComputeEngine initComputeEngine(){
        if(GameStateHandler.getComputerNumber() == 0){
            return new ComputeEngine('X');
        }else{
            return new ComputeEngine('O');
        }
    }

    private void gameLoop(){
        while(!GameStateHandler.isGameOver()){
            if(!GameStateHandler.isPlayerTurn()){
                int location = computeEngine.getMove(GameStateHandler.getBoard());
                boolean botMoveSuccess = GameStateHandler.doComputerMove(location);
                GameStateHandler.setPlayerTurn();
            }

            try{
                Thread.sleep(10);
            }catch(Exception e){}
        }

        dataHandler.saveGame(GameStateHandler.getGame());

        Frame.repaint();
        Utils.sleep(10);

    }
}
