/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.tictactoebot.gameEngine.gameTypes;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.UI.Utils;
import com.tictactoebot.computeEngine.ComputeEngine;
import com.tictactoebot.dataHandler.DataHandler;

import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;

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
            return new ComputeEngine('X', dataHandler);
        }else{
            return new ComputeEngine('O', dataHandler);
        }
    }

    private void gameLoop(){
        while(!GameStateHandler.isGameOver()){
            if (!GameStateHandler.isPlayerTurn()){
                int location = computeEngine.getMove(GameStateHandler.getBoard());
                boolean botMoveSuccess = GameStateHandler.doComputerMove(location);
                GameStateHandler.setPlayerTurn();
            }

            try{
                Thread.sleep(10);
            }catch(Exception e){
            }
        }

        Game game = GameStateHandler.getGame();

        switch(GameStateHandler.getBoard().checkResult()){
            case Board.O_WINS:
                game.setResult('O');
                break;
            case Board.X_WINS:
                game.setResult('X');
                break;
            case Board.TIE:
                game.setResult('T');
                break;
        }

        System.out.println("\nNum Smart Moves Chosen: " + computeEngine.getNumSmartMovesChosen());
        System.out.println("Num Random Moves Chosen: " + computeEngine.getNumRandomMovesChosen());

        dataHandler.saveGame(game, computeEngine.getLetter());

        Frame.repaint();
        Utils.sleep(10);

    }
}
