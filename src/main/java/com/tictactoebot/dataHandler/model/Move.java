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

    // returns the initial empty board - this method is simply for the sake of saving time
    public static Move emptyBoard(){
        return new Move(0, new Board().toString(), -1);
    }
}
