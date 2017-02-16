package com.tictactoebot;


import com.tictactoebot.gameEngine.GameEngine;
import com.tictactoebot.gameEngine.Options;

public class Main {

    public static void main(String[] args){

<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
        addShutdownHook();

        Options options = getOptions(args);

        GameEngine gameEngine = new GameEngine(options);
        gameEngine.run();
    }

    public static Options getOptions(String[] args){

>>>>>>> Stashed changes
        boolean randomTrainer = false; // defaults to false
        int numTrainingGames = -1;

        if(args.length == 2 && args[0].equals("train")){
            randomTrainer = true;
            numTrainingGames = Integer.parseInt(args[1]);
        }

        Options options = new Options();
        options.setRandomTrainerOn(randomTrainer);
        if(randomTrainer){
            options.setNumTrainingGames(numTrainingGames);
        }
        options.setMillisBetweenGames(4000); // 4 second delay between games

        GameEngine gameEngine = new GameEngine(options);
        gameEngine.run();
    }

    public static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                // this code executes on system exit
                System.out.println("\nShutdown Hook Activated...");

                // do some things here

                //TODO: WRITE GAME NUM TO FILE

                System.out.println("Shutdown Hook Executed Successfully.");
            }
        }));
    }

    public static void addShutdownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                // this code executes on system exit
                System.out.println("\nShutdown Hook Activated...");

                // do some things here

                //TODO: WRITE GAME NUM TO FILE

                System.out.println("Shutdown Hook Executed Successfully.");
            }
        }));
    }
}