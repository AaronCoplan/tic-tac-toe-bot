package com.tictactoebot.dataHandler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afcoplan on 2/12/17.
 */
public class Game {

    private int gameNumber;

    private List<Move> moves;
    private int moveNumber;

    public Game(){

        // initialize the list of moves
        this.moves = new ArrayList<Move>();

        // first board position in any given game is an empty board
        this.moves.add(Move.emptyBoard());

        // since we have already added a blank board as move 0, this is move 1
        this.moveNumber =1;
    }

    public void addMove(Board currentBoardState, int indexPlayed){
        Move nextMove = new Move(moveNumber, currentBoardState.toString(), indexPlayed);
        moves.add(nextMove);

        ++moveNumber;
    }

    public void addMove(Board currentBoardState, int rowPlayed, int colPlayed){
        this.addMove(currentBoardState, Board.convert2DIndexTo1D(rowPlayed, colPlayed));
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

    public void setGameNumber(int gameNumber){
        this.gameNumber = gameNumber;
    }
}
