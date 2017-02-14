package com.tictactoebot.gameEngine;

import static com.tictactoebot.gameEngine.GameStateHandler.createGame;

/**
 * Created by afcoplan on 2/13/17.
 */
public class GameEngine {

    private final Options options;


    public GameEngine(Options options){
        this.options = options;
    }

    public void run(){
        createGame(options.isRandomTrainerOn(), options.isResetOnGameEnd(), options.isUsesTerminalOutput());
    }
}
