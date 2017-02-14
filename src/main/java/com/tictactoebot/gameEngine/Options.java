package com.tictactoebot.gameEngine;

/**
 * Created by afcoplan on 2/13/17.
 */
public class Options {

    private boolean randomTrainerOn;
    private boolean resetOnGameEnd;
    private boolean usesTerminalOutput;

    public Options(){}

    public Options(boolean randomTrainerOn, boolean resetOnGameEnd, boolean usesTerminalOutput){
        this.randomTrainerOn = randomTrainerOn;
        this.resetOnGameEnd = resetOnGameEnd;
        this.usesTerminalOutput = usesTerminalOutput;
    }

    public boolean isRandomTrainerOn(){
        return randomTrainerOn;
    }

    public void setRandomTrainerOn(boolean randomTrainerOn){
        this.randomTrainerOn = randomTrainerOn;
    }

    public boolean isResetOnGameEnd(){
        return resetOnGameEnd;
    }

    public void setResetOnGameEnd(boolean resetOnGameEnd){
        this.resetOnGameEnd = resetOnGameEnd;
    }

    public boolean isUsesTerminalOutput(){
        return usesTerminalOutput;
    }

    public void setUsesTerminalOutput(boolean usesTerminalOutput){
        this.usesTerminalOutput = usesTerminalOutput;
    }
}
