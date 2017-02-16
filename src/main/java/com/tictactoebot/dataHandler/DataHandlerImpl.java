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
    public List<Game> findGamesByBoardHash(String boardHash){
        return queryService.findGamesByBoardHash(boardHash);
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
