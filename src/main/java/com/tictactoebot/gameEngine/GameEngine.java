package com.tictactoebot.gameEngine;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.gameEngine.gameTypes.HumanVsBotEngine;
import com.tictactoebot.gameEngine.gameTypes.TrainingEngine;

/**
 * Created by afcoplan on 2/13/17.
 */
public class GameEngine {

    private final Options options;

    public GameEngine(Options options){
        this.options = options;
    }

    public void run(){
        if(options.isRandomTrainerOn()){
            train(options.getNumTrainingGames());
        }else{
            playHumanVsBot(options.isContinuousPlay());
        }
    }

    public void train(int numGames){
        System.out.println("TRAINING MODE");
        try{
            Thread.sleep(5000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        // TODO: training code goes here
        TrainingEngine.train(numGames);
    }

    private void playHumanVsBot(boolean continuousPlay){

        HumanVsBotEngine humanVsBotEngine = new HumanVsBotEngine();

        // this while loop allows the user to play games until they
        // press the close button on the JFrame
        while(true){
            humanVsBotEngine.playGame();
            // wait 5 seconds so the user can see the result
            try{
                Thread.sleep(options.getMillisBetweenGames());
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            //close the window
            Frame.close();
        }
    }
}