package com.tictactoebot.dataHandler.write;

import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.MoveAlreadyWrittenException;
import com.tictactoebot.dataHandler.error.ResultAlreadyWrittenException;
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
        this.storageDirectory = new File(DataHandler.DIRECTORY_PATH);

        if(storageDirectory == null || !storageDirectory.exists()){
            throw new StorageAccessException("Error accessing getMove data files!");
        }
    }

    /*
     *  Methods from Interface
     */

    @Override
    public void writeGame(Game game, List<File> existingFiles) throws MoveAlreadyWrittenException, ResultAlreadyWrittenException {
        final int gameNumber = game.getGameNumber();

        int successCount = 0;

        for(Move m : game.getMoves()){
            boolean success = writeMove(gameNumber, m, existingFiles);
            if(success) ++successCount;
        }

        boolean success = writeResult(gameNumber, game.getResult(), existingFiles);
        if(!success) System.out.println("ERROR: failed to save game result!");

        // TODO: how should we handle the case where a getMove from a game fails to save?
        // should we have it delete all of the successful moves since the game is missing some moves?
        // nummoves + 1 because of the zeroeth getMove with the empty board state
        if(successCount != game.getNumMoves() + 1) System.out.println("ERROR: failed to save all moves!");

    }

    @Override
    public void deleteAllMoves(List<File> existingFiles){
        int successCount = 0;

        for(File f : existingFiles){
            if(f.delete()) ++successCount;
        }

        if(successCount != existingFiles.size()) System.out.println("ERROR DELETING ALL MOVES!");
    }

    @Override
    public boolean initStatsFile(){
        File statsFile = new File(DataHandler.DIRECTORY_PATH + "stats_w0_l0_t0" + DataHandler.STATS_EXTENSION);

        boolean success;
        try{
            statsFile.createNewFile();
            success = true;
        }catch(IOException error){
            error.printStackTrace();
            success = false;
        }

        return success;
    }

    /*
     *  Helper Methods
     */

    private boolean writeResult(int gameNumber, char result, List<File> existingFiles) throws ResultAlreadyWrittenException {
        final String fileName = "result_g" + gameNumber + "_r" + result + DataHandler.RESULT_EXTENSION;

        if(existingFiles.contains(fileName)){
            throw new ResultAlreadyWrittenException(gameNumber);
        }

        File resultFile = new File(DataHandler.DIRECTORY_PATH + fileName);

        boolean success;

        try{
            resultFile.createNewFile();
            success = true;
        }catch(IOException error){
            System.out.println(new File("").getAbsolutePath());
            System.out.println(DataHandler.DIRECTORY_PATH + fileName);
            error.printStackTrace();
            System.out.println("Error saving result.");
            success = false;
        }

        return success;
    }

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
            System.out.println("Error saving getMove!");
            success = false;
        }

        return success;
    }

    private String generateFileName(int gameNumber, int moveNumber, int spotPlayedIndex, String boardHash){
        return "g" + gameNumber + "_m" + moveNumber + "_sp" + spotPlayedIndex + "_" + boardHash + DataHandler.FILE_EXTENSION;
    }
}
