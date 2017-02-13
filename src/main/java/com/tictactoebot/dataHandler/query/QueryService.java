package com.tictactoebot.dataHandler.query;

import java.io.File;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface QueryService {

    int getGameCount();
    List<File> getMoveFileList();
}
