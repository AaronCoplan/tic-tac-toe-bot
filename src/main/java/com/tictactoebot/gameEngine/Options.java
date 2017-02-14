package com.tictactoebot.gameEngine;

/**
 * Created by afcoplan on 2/13/17.
 */
public class Options {

    private boolean randomTrainerOn;
    private boolean isContinuousPlay;

    private int numTrainingGames;

    public Options(){}

    public Options(boolean randomTrainerOn, boolean isContinuousPlay){
        this.randomTrainerOn = randomTrainerOn;
        this.isContinuousPlay = isContinuousPlay;

    }

    public Options(boolean randomTrainerOn, boolean isContinuousPlay, int numTrainingGames){
        this.randomTrainerOn = randomTrainerOn;
        this.isContinuousPlay = isContinuousPlay;
        this.numTrainingGames = numTrainingGames;
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
}
