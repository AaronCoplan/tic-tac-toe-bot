package com.tictactoebot;


import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.gameEngine.GameEngine;
import com.tictactoebot.gameEngine.Options;

public class Main {

    public static void main(String[] args){

        DataHandler dataHandler = DataHandler.getInstance();

        addShutdownHook(dataHandler);

        Options options = getOptions(args);

        GameEngine gameEngine = new GameEngine(dataHandler, options);
        gameEngine.run();
    }

    public static Options getOptions(String[] args){

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

        return options;
    }


    public static void addShutdownHook(DataHandler dataHandler){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                // this code executes on system exit
                System.out.println("\nShutdown Hook Activated...");

                // do some things here

                //TODO: WRITE GAME NUM TO FILE

                /*
                 *  Pseudocode:
                 *
                 *  delete stats file (method call to data handler)
                 *  create a new stats file (another call to data handler)
                 */
                dataHandler.resetStats(); // this deletes the existing (if any) stats file
                dataHandler.writeStats(); // resaves the stats

                System.out.println("Shutdown Hook Executed Successfully.");
            }
        }));
    }
}