package com.tictactoebot.dataHandler;

import com.tictactoebot.dataHandler.error.StorageAccessException;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface DataHandler {

    static final String DIRECTORY_PATH = "storage/";
    static final String FILE_EXTENSION = ".move";

    // singleton initialization practice
    // TODO: ensure there is only one instance of DataHandler
    static DataHandler getInstance() {
        try{
            return new DataHandlerImpl();
        }catch(StorageAccessException error){
            return null;
        }
    }

    /*
     *  This method will determine the next game number in sequence and assign it to the requesting game
     */
    int assignGameNumber();


}
