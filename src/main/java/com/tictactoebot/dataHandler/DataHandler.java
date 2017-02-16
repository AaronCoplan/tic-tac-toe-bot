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
    List<Game> findGamesByBoardHash(String boardHash);

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
}
