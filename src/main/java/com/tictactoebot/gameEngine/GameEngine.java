package com.tictactoebot.gameEngine;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.gameEngine.gameTypes.HumanVsBotEngine;
import com.tictactoebot.gameEngine.gameTypes.TrainingEngine;

/**
 * Created by afcoplan on 2/13/17.
 */
public class GameEngine {

    private final Options options;
    private final DataHandler dataHandler;
    //private final Game game;

    public GameEngine(DataHandler dataHandler, Options options){
        this.dataHandler = dataHandler;
        this.options = options;
    }

    public void run(){
        if(options.isRandomTrainerOn()){
            train(options.getNumTrainingGames());
        }else{
            options.setContinuousPlay(Frame.askContinuous());
            playHumanVsBot(options.isContinuousPlay());
        }
    }

    public void train(int numGames){
        System.out.println("TRAINING MODE: " + numGames + " games");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        TrainingEngine.train(numGames, dataHandler);
    }

    private void playHumanVsBot(boolean continuousPlay){

        HumanVsBotEngine humanVsBotEngine = new HumanVsBotEngine(dataHandler);

        // this while loop allows the user to play games until they
        // press the close button on the JFrame
        do {
            humanVsBotEngine.playGame();
            // wait 5 seconds so the user can see the result

            try{
                Thread.sleep(options.getMillisBetweenGames());
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            //close the window
            Frame.close();
        } while (continuousPlay);
    }
}
