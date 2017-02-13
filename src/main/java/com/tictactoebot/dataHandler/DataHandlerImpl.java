package com.tictactoebot.dataHandler;

import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.query.QueryService;
import com.tictactoebot.dataHandler.query.QueryServiceImpl;
import com.tictactoebot.dataHandler.write.DataWriter;
import com.tictactoebot.dataHandler.write.DataWriterImpl;

public class DataHandlerImpl implements DataHandler {

    private final DataWriter dataWriter;
    private final QueryService queryService;

    protected DataHandlerImpl() throws StorageAccessException {
        this.dataWriter = new DataWriterImpl();
        this.queryService = new QueryServiceImpl();
    }

    @Override
    public int assignGameNumber(){
        return queryService.getNextGameNumber();
    }

    @Override
    public boolean saveGame(Game game){
        boolean success;

        try{
            dataWriter.writeGame(game, queryService.getMoveFileList());
            success = true;
        }catch(Exception e){
            success = false;
        }

        return success;
    }
}
