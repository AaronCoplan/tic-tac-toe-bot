package com.tictactoebot.dataHandler.stats;

/**
 * Created by afcoplan on 2/16/17.
 */
public class StatsServiceImpl implements StatsService {

    private int winCount;
    private int lossCount;
    private int tieCount;

    public StatsServiceImpl(){
        this.winCount = 0;
        this.lossCount = 0;
        this.tieCount = 0;
    }

    @Override
    public void init(String data){
        if(data == null){
            System.out.println("ERROR LOADING STATS");
            System.exit(-1);
        }

        // parse and store data
        String[] pieces = data.split("_");
        this.winCount = Integer.parseInt(pieces[1].substring(1));
        this.lossCount = Integer.parseInt(pieces[2].substring(1));
        this.tieCount = Integer.parseInt(pieces[3].substring(1));

        this.displayStats();
    }

    @Override
    public void displayStats(){
        System.out.println("Number of wins: " + winCount);
        System.out.println("Number of losses: " + lossCount);
        System.out.println("Number of ties: " + tieCount);
    }

    @Override
    public void addWin(){
        ++winCount;
    }

    @Override
    public void addLoss(){
        ++lossCount;
    }

    @Override
    public void addTie(){
        ++tieCount;
    }

    @Override
    public String getData(){
        return "stats_w" + this.winCount + "_l" + this.lossCount + "_t" + this.tieCount;
    }
}
