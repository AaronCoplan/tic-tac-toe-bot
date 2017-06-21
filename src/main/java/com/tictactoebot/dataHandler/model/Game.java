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
