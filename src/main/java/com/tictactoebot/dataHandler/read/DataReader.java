package com.tictactoebot.dataHandler.read;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface DataReader {

    List<File> getMoveFileList();
    List<String> getMoveFileNames();
    List<String> getResultFileNames();
    List<File> getResultFileList();

    List<String> getResultFilesFromSearchTerm(String searchTerm);
    List<String> getMoveFilesFromSearchTerm(String searchTerm);

    int getNumGamesFromBoardHash(String boardHash);
    HashMap<Integer, Boolean> getHashMapGamesFromBoardHash(String boardHash);

    String getStatsFileName();
}
