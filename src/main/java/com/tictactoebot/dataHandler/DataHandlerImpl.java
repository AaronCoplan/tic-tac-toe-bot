package com.tictactoebot.dataHandler;

import com.tictactoebot.dataHandler.error.SingletonCreationException;
import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.query.QueryService;
import com.tictactoebot.dataHandler.query.QueryServiceImpl;
import com.tictactoebot.dataHandler.write.DataWriter;
import com.tictactoebot.dataHandler.write.DataWriterImpl;

import java.util.List;

public class DataHandlerImpl implements DataHandler {

    private static boolean instanceCreated = false;

    private final DataWriter dataWriter;
    private final QueryService queryService;

    protected DataHandlerImpl() throws StorageAccessException, SingletonCreationException {
        if(instanceCreated) throw new SingletonCreationException();

        this.dataWriter = new DataWriterImpl();
        this.queryService = new QueryServiceImpl();

        instanceCreated = true;
    }

    @Override
    public boolean saveGame(Game game){
        boolean success;

        try{
            int gameNumber = queryService.getNextGameNumber();
            game.setGameNumber(gameNumber);

            dataWriter.writeGame(game, queryService.getMoveFileList());
            success = true;
        }catch(Exception e){
            success = false;
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
}
