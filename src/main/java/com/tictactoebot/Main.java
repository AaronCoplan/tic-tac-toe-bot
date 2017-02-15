package com.tictactoebot;


import com.tictactoebot.gameEngine.GameEngine;
import com.tictactoebot.gameEngine.Options;

public class Main {

    public static void main(String[] args){

        Options options = new Options();
        options.setRandomTrainerOn(false);
        options.setMillisBetweenGames(4000); // 4 second delay between games

        GameEngine gameEngine = new GameEngine(options);
        gameEngine.run();
    }
}