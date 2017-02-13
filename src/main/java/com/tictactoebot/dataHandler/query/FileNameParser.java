package com.tictactoebot.dataHandler.query;

import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.model.Move;

import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class FileNameParser {

    public static int getGameNumber(String fileName){
        return Integer.parseInt(fileName.substring(1, fileName.indexOf('_')));
    }

    public static String getBoardHash(String fileName){
        String s = fileName.substring(fileName.lastIndexOf('_') + 1);
        return s;
    }

    public static int getSpotPlayedIndex(String fileName){
        final String searchTerm = "_sp";

        String portion = fileName.substring(fileName.indexOf("_sp") + searchTerm.length());
        portion = portion.substring(0, portion.indexOf('_'));

        return Integer.parseInt(portion);
    }

    public static Game parseGameFromMoveList(List<String> moves){
        if(moves.size() == 0) return null;

        Game game = new Game();

        int gameNumber = FileNameParser.getGameNumber(moves.get(0));

        for(int moveNumber = 0; moveNumber < moves.size(); ++moveNumber){
            String moveFileName = moves.get(moveNumber);

            System.out.println(moveFileName);

            Move m = new Move(moveNumber, FileNameParser.getBoardHash(moveFileName), FileNameParser.getSpotPlayedIndex(moveFileName));
        }

        return game;
    }
}
