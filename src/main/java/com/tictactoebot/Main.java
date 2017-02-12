package com.tictactoebot;

import static com.tictactoebot.UI.GameStateHandler.createGame;

public class Main {

    public static void main(String[] args){

        createGame(true, true, false);

        System.out.println("Hello.");
    }
}