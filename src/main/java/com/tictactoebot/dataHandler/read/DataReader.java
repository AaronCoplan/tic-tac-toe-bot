package com.tictactoebot.dataHandler.read;

import java.io.File;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface DataReader {

    List<File> getMoveFileList();
    List<String> getMoveFileNames();
    List<String> getResultFileNames();
    List<File> getResultFileList();
    String getStatsFileName();
}
