package com.tictactoebot.dataHandler.model;

import com.tictactoebot.dataHandler.error.IllegalMoveException;

/*
 *  This class allows us to treat a 1D char array as a 2D game board.
 */
public class Board {

    private final int BOARD_SIZE = 9;
    private final int ROW_SIZE = 3;
    private final int COL_SIZE = 3;

    private final char[] board;

    public Board(){
        this.board = new char[BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; ++i){
            board[i] = '-';
        }
    }

    public static int convert2DIndexTo1D(int row, int col){
        return (row * 3) + col;
    }

    // method to set the character at the specified row and column
    public void setChar(int row, int col, char value) throws IllegalMoveException {
        int index = Board.convert2DIndexTo1D(row, col);

        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        if(board[index] != '-') throw new IllegalMoveException();

        board[index] = value;
    }

    // method to set the character knowing the index in the 1D array
    public void setChar(int index, char value) throws IllegalMoveException {
        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        if(board[index] != '-') throw new IllegalMoveException();

        board[index] = value;
    }

    // method to get the character knowing the index in the 1D array
    public char getChar(int index){
        if(index >= BOARD_SIZE || index < 0){
            throw new IndexOutOfBoundsException();
        }

        return board[index];
    }

    //Checks if the board tile at the index is empty
    public boolean isOccupied(int index){
        return board[index] != '-';
    }

    //Checks if the board tile at the row and column is empty
    public boolean isOccupied(int row, int col){
        return board[convert2DIndexTo1D(row, col)] != '-';
    }

    // method to get the character at the specified row and column
    public char getChar(int row, int col){

        int index = Board.convert2DIndexTo1D(row, col);

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

    public void prettyPrint(){
        for(int row = 0; row < ROW_SIZE; ++row){
            for(int col = 0; col < COL_SIZE; ++col){
                System.out.print(this.getChar(row,col));
                if(col < COL_SIZE - 1) System.out.print('|');
            }
            System.out.println();
        }
    }
}
