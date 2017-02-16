package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.read.DataReader;
import com.tictactoebot.dataHandler.read.DataReaderImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class QueryServiceImpl implements QueryService {

    private final DataReader dataReader;

    private int gameNumber;

    public QueryServiceImpl() throws StorageAccessException {

        this.dataReader = new DataReaderImpl();
        this.gameNumber = -1;
    }

    @Override
    public int getNextGameNumber(){
        if(gameNumber == -1){
            List<String> moveFileNames = dataReader.getMoveFileNames();

            if (moveFileNames.size() == 0) return 1;

            System.out.println("Locating next GAME ID in sequence...");
            int i = 0;
            while(true){
                Game g = findGameByGameNumber(i);
                if (g == null){
                    System.out.println("GAME ID: " + i);
                    gameNumber = i; // caching - saves an outrageous amount of computing
                    return i;
                }
                ++i;
            }
        }else{
            ++gameNumber;
            return gameNumber;
        }
    }

    @Override
    public List<File> getMoveFileList(){
        return dataReader.getMoveFileList();
    }

    @Override
    public List<File> getResultFileList(){
        return dataReader.getResultFileList();
    }

    private char findResultByGameNumber(int gameNumber){
        final String searchTerm = "result_g" + gameNumber + "_r";

        List<String> resultFileNames = dataReader.getResultFileNames();
        resultFileNames.removeIf(s -> !s.contains(searchTerm));

        String fileName = resultFileNames.get(0);

        return FileNameParser.parseResult(fileName);
    }

    @Override
    public Game findGameByGameNumber(int gameNumber){
        final String searchString = "g" + gameNumber + "_";

        List<String> fileNames = dataReader.getMoveFileNames();
        fileNames.removeIf(s -> !s.contains(searchString));

        if(fileNames.size() == 0) return null;

        Collections.sort(fileNames);

        Game game = FileNameParser.parseGameFromMoveList(fileNames);
        game.setResult(this.findResultByGameNumber(gameNumber));

        return game;
    }

    @Override
    public List<Game> findGamesByBoardHash(String boardHash){
        final String searchString = "_" + boardHash;

        List<String> fileNames = dataReader.getMoveFileNames();
        fileNames.removeIf(s -> !s.contains(searchString));

        List<Game> games = new ArrayList<Game>();

        for(String fileName : fileNames){
            int gameNumber = FileNameParser.getGameNumber(fileName);

            Game g = this.findGameByGameNumber(gameNumber);

            games.add(g);
        }

        return games;
    }

    @Override
    public List<Game> findWinningGamesByBoardHash(String boardHash, char letter){
        List<Game> matchingGames = this.findGamesByBoardHash(boardHash);
        matchingGames.removeIf(s -> s.getResult() != letter);

        return matchingGames;
    }

    @Override
    public String fetchStatsData(){
        return dataReader.getStatsFileName();
    }

}
