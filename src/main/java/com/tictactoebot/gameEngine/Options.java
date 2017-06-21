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

/**
 * Created by afcoplan on 2/13/17.
 */
public class Options {

    private boolean randomTrainerOn;
    private boolean isContinuousPlay;

    private int numTrainingGames;
    private long millisBetweenGames;

    public ConnectionInfo connectionInfo;

    private int port;

    public Options(){
        // default option
        this.millisBetweenGames = 5000;
    }

    public Options(boolean randomTrainerOn, boolean isContinuousPlay){
        this.randomTrainerOn = randomTrainerOn;
        this.isContinuousPlay = isContinuousPlay;

        // default option
        this.millisBetweenGames = 5000;

    }

    public Options(boolean randomTrainerOn, boolean isContinuousPlay, int numTrainingGames){
        this.randomTrainerOn = randomTrainerOn;
        this.isContinuousPlay = isContinuousPlay;
        this.numTrainingGames = numTrainingGames;

        // default option
        this.millisBetweenGames = 5000;
    }

    public boolean isRandomTrainerOn(){
        return randomTrainerOn;
    }

    public void setRandomTrainerOn(boolean randomTrainerOn){
        this.randomTrainerOn = randomTrainerOn;
    }

    public boolean isContinuousPlay(){
        return isContinuousPlay;
    }

    public void setContinuousPlay(boolean continuousPlay){
        this.isContinuousPlay = continuousPlay;
    }

    public int getNumTrainingGames(){
        return numTrainingGames;
    }

    public void setNumTrainingGames(int numTrainingGames){
        this.numTrainingGames = numTrainingGames;
    }

    public long getMillisBetweenGames(){

        return millisBetweenGames;
    }

    public void setMillisBetweenGames(long millisBetweenGames){
        this.millisBetweenGames = millisBetweenGames;
    }
}
