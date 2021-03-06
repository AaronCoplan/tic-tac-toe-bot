/*
MIT License

Copyright (c) 2017 Aaron Coplan
Copyright (c) 2017 Andrew Adalian
Copyright (c) 2017 Devin Kopp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.read.DataReader;
import com.tictactoebot.dataHandler.read.DataReaderImpl;

import java.io.File;
import java.util.*;

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

        List<String> resultFileNames = dataReader.getResultFilesFromSearchTerm(searchTerm);
        String GameResultFileName = resultFileNames.get(0);

        return FileNameParser.parseResult(GameResultFileName);
    }

    @Override
    public Game findGameByGameNumber(int gameNumber){
        final String searchString = "g" + gameNumber + "_";

        List<String> fileNames = dataReader.getMoveFilesFromSearchTerm(searchString);

        if(fileNames.size() == 0) return null;

        Collections.sort(fileNames);

        Game game = FileNameParser.parseGameFromMoveList(fileNames);
        game.setResult(this.findResultByGameNumber(gameNumber));

        return game;
    }

    @Override
    public List<Game> findGamesByBoardHash(String boardHash){
        final String searchString = "_" + boardHash;

        List<String> fileNames = dataReader.getMoveFilesFromSearchTerm(searchString);

        List<Game> games = new ArrayList<Game>();

        for(String fileName : fileNames){
            int gameNumber = FileNameParser.getGameNumber(fileName);

            Game g = this.findGameByGameNumber(gameNumber);

            games.add(g);
        }

        return games;
    }

    public int findNumGamesByBoardHash(String boardHash){
       return dataReader.getNumGamesFromBoardHash(boardHash);
    }


    @Override
    //TODO: could be optimized by having a separate method in DataReader that takes 2 search terms, instead of creating the boardhash list and removing the items.
    public List<Game> findWinningGamesByBoardHash(String boardHash, char letter){
        List<Game> matchingGames = this.findGamesByBoardHash(boardHash);
        matchingGames.removeIf(s -> s.getResult() != letter);

        return matchingGames;
    }

    @Override
    public int findNumWinningGamesByBoardHash(String boardHash, char letter){
        HashMap<Integer, Boolean> uniqueGames = dataReader.getHashMapGamesFromBoardHash(boardHash);
        int totalWinningGames = 0;

        char result;
        Iterator it = uniqueGames.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            result = this.findResultByGameNumber((Integer)pair.getKey());
            if(result == letter /*|| result == 'T'*/){  //Uncomment this to get how many wins/ ties.
                totalWinningGames++;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        return totalWinningGames;
    }


    @Override
    public int findNumWinningTieGamesByBoardHash(String boardHash, char letter){
        HashMap<Integer, Boolean> uniqueGames = dataReader.getHashMapGamesFromBoardHash(boardHash);
        int totalWinningGames = 0;

        char result;
        Iterator it = uniqueGames.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            result = this.findResultByGameNumber((Integer)pair.getKey());
            if(result == letter || result == 'T'){
                totalWinningGames++;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

        return totalWinningGames;
    }



    @Override
    public String fetchStatsData(){
        return dataReader.getStatsFileName();
    }

}
