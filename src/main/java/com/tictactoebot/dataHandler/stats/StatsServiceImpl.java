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
        this.tieCount = Integer.parseInt(pieces[3].substring(1, pieces[3].indexOf('.')));

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
