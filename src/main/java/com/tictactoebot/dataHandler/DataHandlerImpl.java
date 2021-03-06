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

package com.tictactoebot.dataHandler;

import com.tictactoebot.dataHandler.error.SingletonCreationException;
import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.query.QueryService;
import com.tictactoebot.dataHandler.query.QueryServiceImpl;
import com.tictactoebot.dataHandler.stats.StatsService;
import com.tictactoebot.dataHandler.stats.StatsServiceImpl;
import com.tictactoebot.dataHandler.write.DataWriter;
import com.tictactoebot.dataHandler.write.DataWriterImpl;

import java.util.List;

public class DataHandlerImpl implements DataHandler {

    private static boolean instanceCreated = false;

    private final DataWriter dataWriter;
    private final QueryService queryService;
    private final StatsService statsService;

    protected DataHandlerImpl() throws StorageAccessException, SingletonCreationException {
        if(instanceCreated) throw new SingletonCreationException();

        this.dataWriter = new DataWriterImpl();
        this.queryService = new QueryServiceImpl();
        this.statsService = new StatsServiceImpl();

        instantiateStats();

        this.resetStats();

        instanceCreated = true;
    }

    private void instantiateStats(){
        String statsData = queryService.fetchStatsData();

        if(statsData == null){

            dataWriter.initStatsFile();
            statsData = queryService.fetchStatsData();

        }

        statsService.init(statsData);
    }

    @Override
    public boolean saveGame(Game game, char computerLetter){
        boolean success;

        try{
            int gameNumber = queryService.getNextGameNumber();
            game.setGameNumber(gameNumber);

            dataWriter.writeGame(game, queryService.getMoveFileList());
            success = true;
        }catch(Exception e){
            e.printStackTrace();
            success = false;
        }

        if(success){
            updateStats(game, computerLetter);
        }

        return success;
    }

    @Override
    public Game findGameByGameNumber(int gameNumber){
        return queryService.findGameByGameNumber(gameNumber);
    }

    @Override

    public int findNumGamesByBoardHash(String boardHash){
        return queryService.findNumGamesByBoardHash(boardHash);
    }

    @Override
    public int findNumWinningGamesByBoardHash(String boardHash, char letter){
        return queryService.findNumWinningGamesByBoardHash(boardHash, letter);
    }

    @Override
    public int findNumWinningTieGamesByBoardHash(String boardHash, char letter){
        return queryService.findNumWinningTieGamesByBoardHash(boardHash, letter);
    }

    @Override
    public  List<Game> findGamesByBoardHash(String boardHash){
        return queryService.findGamesByBoardHash(boardHash);
    }

    @Override
    public  List<Game> findWinningGamesByBoardHash(String boardHash, char letter){
        return queryService.findWinningGamesByBoardHash(boardHash, letter);
    }

    @Override
    public void deleteAllMoves(){
        dataWriter.deleteAllMoves(queryService.getMoveFileList());
    }

    @Override
    public void deleteAllResults(){
        dataWriter.deleteAllMoves(queryService.getResultFileList());
    }

    @Override
    public void resetStats(){
        dataWriter.deleteStats(queryService.fetchStatsData());
    }

    @Override
    public boolean writeStats(){
        return dataWriter.writeStats(statsService.getData());
    }

    private void updateStats(Game game, final char computerLetter){
        char result = game.getResult();

        if(result == 'T'){
            statsService.addTie();
        }else if(result == computerLetter){
            statsService.addWin();
        }else{
            statsService.addLoss();
        }

        statsService.displayStats();
    }
}
