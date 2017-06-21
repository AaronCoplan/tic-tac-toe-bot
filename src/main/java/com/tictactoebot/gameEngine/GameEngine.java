/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

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
