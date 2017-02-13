package com.tictactoebot.dataHandler.model;

/*
 *  Immutable representation of a single Move
 *  Contains move number and a hash of the board state
 */
public class Move {

    private final int moveNumber;
    private final String boardHash;
    private final int spotPlayedIndex;

    public Move(int moveNumber, String boardHash, int spotPlayedIndex){
        this.moveNumber = moveNumber;
        this.boardHash = boardHash;
        this.spotPlayedIndex = spotPlayedIndex;
    }

    public String getBoardHash(){
        return this.boardHash;
    }

    public int getMoveNumber(){
        return this.moveNumber;
    }

    public int getSpotPlayedIndex(){
        return this.spotPlayedIndex;
    }

    public static Move emptyBoard(){
        return new Move(0, new Board().toString(), -1);
    }
}
