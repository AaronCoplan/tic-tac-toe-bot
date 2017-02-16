package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.UI.Utils;
import com.tictactoebot.computeEngine.ComputeEngine;
<<<<<<< Updated upstream
=======
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;
>>>>>>> Stashed changes
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

/**
 * Created by afcoplan on 2/13/17.
 */
public class HumanVsBotEngine {
    private ComputeEngine computeEngine;

    public HumanVsBotEngine(){}

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

<<<<<<< Updated upstream
    }


=======
        Game game = GameStateHandler.getGame();

        switch(GameStateHandler.getBoard().checkResult())
        {
            case Board.O_WINS:  game.setResult('O');
                                break;
            case Board.X_WINS:  game.setResult('X');
                                break;
            case Board.TIE:     game.setResult('T');
                                break;
        }

        dataHandler.saveGame(game, computeEngine.getLetter());
>>>>>>> Stashed changes


}
