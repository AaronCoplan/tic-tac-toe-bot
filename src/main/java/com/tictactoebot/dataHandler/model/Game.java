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
    private char result;

    public Game(){

        // initialize the list of moves
        this.moves = new ArrayList<Move>();

        // first board position in any given game is an empty board
        this.moves.add(Move.emptyBoard());

        // since we have already added a blank board as getMove 0, this is getMove 1
        this.moveNumber = 1;
    }

    public void addMove(Move move){
        moves.add(move);
        moveNumber++;
    }

    public void addMove(Board currentBoardState, int indexPlayed){
       this.addMove(new Move(moveNumber, currentBoardState.toString(), indexPlayed));
    }

    public void addMove(Board currentBoardState, int rowPlayed, int colPlayed){
        this.addMove(new Move(moveNumber, currentBoardState.toString(), Board.convert2DIndexTo1D(rowPlayed, colPlayed)));
    }

    public char getResult(){
        return result;
    }

    public void setResult(char result){
        this.result = result;
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
