package com.tictactoebot.dataHandler.query;

/**
 * Created by afcoplan on 2/12/17.
 */
public class FileNameParser {

    public static int getGameNumber(String fileName){
        return Integer.parseInt(fileName.substring(1, fileName.indexOf('_')));
    }
}
