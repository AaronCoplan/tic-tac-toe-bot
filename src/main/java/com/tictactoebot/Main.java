package com.tictactoebot;

import static com.tictactoebot.UI.GameStateHandler.createGame;

public class Main {

    public static void main(String[] args){

        createGame(false, false, false);

        System.out.println("Hello.");
    }
}