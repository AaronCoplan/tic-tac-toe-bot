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

package com.tictactoebot.dataHandler;

import com.tictactoebot.dataHandler.error.SingletonCreationException;
import com.tictactoebot.dataHandler.error.StorageAccessException;
import com.tictactoebot.dataHandler.model.Game;

import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public interface DataHandler {

    String DIRECTORY_PATH = "storage/";
    String FILE_EXTENSION = ".move";
    String RESULT_EXTENSION = ".result";
    String STATS_EXTENSION = ".stats";

    /*
     *  DataHandler follows a singleton initialization paradigm. This ensures that
     *  there is only ever one instance of DataHandler.  By having only one instance,
     *  we prevent issues with concurrent access and thus avoid corrupting our data.
     */
    static DataHandler getInstance() {
        try{
            return new DataHandlerImpl();
        }catch(StorageAccessException error){
            return null;
        }catch(SingletonCreationException error){
            return null;
        }
    }

    /*
     *  This method will save a game to the data storage
     *  Takes two params: game object to save, and the letter
     *  the computer played as for stat tracking purposes
     */
    boolean saveGame(Game game, char computerLetter);

    /*
     *  Returns the game with the corresponding game number, or null if none
     */
    Game findGameByGameNumber(int gameNumber);

    /*
     *  Returns a list of full game objects containing the designated hash
     */

    int findNumGamesByBoardHash(String boardHash);

    int findNumWinningGamesByBoardHash(String boardHash, char letter);

    int findNumWinningTieGamesByBoardHash(String boardHash, char letter);

    List<Game> findGamesByBoardHash(String boardHash);

    /*
     *  Returns a list of full game objects that contain the designated hash and result in a win
     */
    List<Game> findWinningGamesByBoardHash(String boardHash, char letter);

    /*
     *  Deletes all stored moves
     */
    void deleteAllMoves();

    /*
     *  Deletes all stored results
     */
    void deleteAllResults();

    /*
     *  Deletes stats data file
     */
    void resetStats();


    /*
     *  Writes stats file to disk
     */
    boolean writeStats();
}
