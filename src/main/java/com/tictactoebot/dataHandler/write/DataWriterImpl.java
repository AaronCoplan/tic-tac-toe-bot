package com.tictactoebot.dataHandler.write;

import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.MoveAlreadyWrittenException;
import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.model.Move;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class DataWriterImpl implements DataWriter {

    private final File storageDirectory;

    public DataWriterImpl() throws StorageAccessException{
        // TODO: ensure you can access the storage directory!

        this.storageDirectory = new File(DataHandler.DIRECTORY_PATH);

        if(storageDirectory == null || !storageDirectory.exists()){
            throw new StorageAccessException("Error accessing move data files!");
        }
    }

    /*
     *  Methods from Interface
     */

    @Override
    public void writeGame(Game game, List<File> existingFiles) throws MoveAlreadyWrittenException{
        final int gameNumber = game.getGameNumber();

        int successCount = 0;

        for(Move m : game.getMoves()){
            boolean success = writeMove(gameNumber, m, existingFiles);
            if(success) ++successCount;
        }

        // TODO: how should we handle the case where a move from a game fails to save?
        // should we have it delete all of the successful moves since the game is missing some moves?
        // nummoves + 1 because of the zeroeth move with the empty board state
        if(successCount != game.getNumMoves() + 1) System.out.println("ERROR: failed to save all moves!");

    }

    /*
     *  Helper Methods
     */

    private boolean writeMove(int gameNumber, Move move, List<File> existingFiles) throws MoveAlreadyWrittenException {
        final String fileName = generateFileName(gameNumber, move.getMoveNumber(), move.getSpotPlayedIndex(), move.getBoardHash());

        if(existingFiles.contains(fileName)){
            throw new MoveAlreadyWrittenException(gameNumber, move.getMoveNumber());
        }

        File moveFile = new File(DataHandler.DIRECTORY_PATH + fileName);

        boolean success;

        try{
            moveFile.createNewFile();
            success = true;
        }catch(IOException error){
            System.out.println("Error saving move!");
            success = false;
        }

        return success;
    }

    private String generateFileName(int gameNumber, int moveNumber, int spotPlayedIndex, String boardHash){
        return "g" + gameNumber + "_m" + moveNumber + "_sp" + spotPlayedIndex + "_" + boardHash + DataHandler.FILE_EXTENSION;
    }
}
