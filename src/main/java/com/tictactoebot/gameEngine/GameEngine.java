package com.tictactoebot.gameEngine;

import com.tictactoebot.UI.Frame;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.gameEngine.gameTypes.ClientEngine;
import com.tictactoebot.gameEngine.gameTypes.HostEngine;
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
        } else if (options.connectionInfo == null){
            options.setContinuousPlay(Frame.askContinuous());
            playHumanVsBot(options.isContinuousPlay());
        } else if (options.connectionInfo.isClient()){
            clientBotVsBot(options.getNumTrainingGames(), options.connectionInfo.getIp(), options.connectionInfo.getPort());
        } else if (options.connectionInfo.isHost()) {
            hostBotVsBot(options.connectionInfo.getPort());
        }
    }

    public void train(int numGames){
        System.out.println("TRAINING MODE: " + numGames + " games");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        TrainingEngine.train(numGames, dataHandler);
    }

    public void hostBotVsBot(int port){
        System.out.println("BotVsBot HOST Training Mode:");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        new HostEngine(port, dataHandler);
    }

    public void clientBotVsBot(int numGames, String ip, int port){
        System.out.println("BotVsBot CLIENT Training Mode: " + numGames + " games");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}

        new ClientEngine(ip, port, numGames, dataHandler);
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
