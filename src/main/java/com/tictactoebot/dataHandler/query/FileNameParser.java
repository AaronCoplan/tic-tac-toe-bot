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

            Move m = new Move(moveNumber, FileNameParser.getBoardHash(moveFileName), FileNameParser.getSpotPlayedIndex(moveFileName));
        }

        return game;
    }

    public static char parseResult(String fileName){
        String portion = fileName.substring(fileName.lastIndexOf("_r") + 2);

        return portion.charAt(0);
    }
}
