package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.model.Game;

import java.io.File;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface QueryService {

    int getNextGameNumber();
    List<File> getMoveFileList();
    List<File> getResultFileList();

    Game findGameByGameNumber(int gameNumber);

    int findNumGamesByBoardHash(String boardHash);

    List<Game> findGamesByBoardHash(String boardHash);

    int findNumWinningGamesByBoardHash(String boardHash, char letter);

    int findNumWinningTieGamesByBoardHash(String boardHash, char letter);

    List<Game> findWinningGamesByBoardHash(String boardHash, char letter);

    String fetchStatsData();
}
