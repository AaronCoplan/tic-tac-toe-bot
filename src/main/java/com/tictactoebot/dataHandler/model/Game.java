package com.tictactoebot.dataHandler.model;

import com.tictactoebot.dataHandler.DataHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class Game {

    private final int gameNumber;

    private List<Move> moves;
    private int moveNumber;

    public Game(DataHandler dataHandler){
        this.gameNumber = dataHandler.assignGameNumber();

        // initialize the list of moves
        this.moves = new ArrayList<Move>();

        // first board position in any given game is an empty board
        this.moves.add(Move.emptyBoard());

        this.moveNumber = 2;
    }

    public void addMove(String boardHash, int indexPlayed){
        Move nextMove = new Move(moveNumber, boardHash, indexPlayed);
        moves.add(nextMove);

        ++moveNumber;
    }

    public List<Move> getMoves(){
        return this.moves;
    }

    public int getNumMoves(){
        return this.moveNumber - 1;
    }

    public int getGameNumber(){
        return this.gameNumber;
    }
}
