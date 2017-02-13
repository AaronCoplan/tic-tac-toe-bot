package com.tictactoebot.dataHandler.model;

/*
 *  This class allows us to treat a 1D char array as a 2D game board.
 */
public class Board {

    private final int BOARD_SIZE = 9;

    private char[] board;

    public Board(){
        this.board = new char[BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; ++i){
            board[i] = '-';
        }
    }

    // method to set the character at the specified row and column
    public void setChar(int row, int col, char value){
        int index = (row * 3) + col;

        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        board[index] = value;
    }

    // method to set the character knowing the index in the 1D array
    public void setChar(int index, char value){
        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        board[index] = value;
    }

    // method to get the character knowing the index in the 1D array
    public char getChar(int index, char value){
        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        return board[index];
    }

    // method to get the character at the specified row and column
    public char getChar(int row, int col){

        int index = (row * 3) + col;

        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        return board[index];
    }

    @Override
    public String toString(){
        String str = "";

        for(int i = 0; i < BOARD_SIZE; ++i){
            str += board[i];
        }

        return str;
    }
}
