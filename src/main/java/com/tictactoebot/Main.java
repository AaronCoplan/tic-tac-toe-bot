package com.tictactoebot;

import com.tictactoebot.dataHandler.DataHandler;

public class Main {

    public static void main(String[] args){

        test();

        System.out.println("Hello.");
    }

    public static void test(){
        DataHandler dataHandler = DataHandler.getInstance();
        if(dataHandler == null){
            System.out.println("Error instantiating data handler!");
            System.exit(-1);
        }

        dataHandler.assignGameNumber();
    }
}