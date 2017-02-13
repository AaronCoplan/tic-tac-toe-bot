package com.tictactoebot.dataHandler.write;

import com.tictactoebot.dataHandler.error.MoveAlreadyWrittenException;
import com.tictactoebot.dataHandler.error.ResultAlreadyWrittenException;
import com.tictactoebot.dataHandler.model.Game;

import java.io.File;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface DataWriter {

    /*
     *  Program waits until the entire game is over to ensure that the moves from the previous game
     *  are not used in any of the decision making.  Should only look at past, complete games as the
     *  current game would not be useful anyways.
     */
    void writeGame(Game game, List<File> existingFiles) throws MoveAlreadyWrittenException, ResultAlreadyWrittenException;

    void deleteAllMoves(List<File> existingFiles);
}
