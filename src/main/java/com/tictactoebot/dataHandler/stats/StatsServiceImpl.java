package com.tictactoebot.dataHandler.stats;

/**
 * Created by afcoplan on 2/16/17.
 */
public class StatsServiceImpl implements StatsService {

    private int winCount;
    private int lossCount;
    private int tieCount;

    private int avgMoveCount;

    public StatsServiceImpl(){
        this.winCount = 0;
        this.lossCount = 0;
        this.tieCount = 0;

        this.avgMoveCount = 0;
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

        this.avgMoveCount = Integer.parseInt(pieces[4].substring(3, pieces[4].indexOf('.')));

        this.displayStats();
    }

    @Override
    public void displayStats(){
        System.out.println("Number of wins: " + winCount);
        System.out.println("Number of losses: " + lossCount);
        System.out.println("Number of ties: " + tieCount);
        System.out.println("Average Move Count: " + avgMoveCount);
    }

}
