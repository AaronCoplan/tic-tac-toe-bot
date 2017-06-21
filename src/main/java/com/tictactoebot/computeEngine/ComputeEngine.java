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

package com.tictactoebot.computeEngine;

import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.error.IllegalMoveException;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Devin on 2/14/2017.
 */
public class ComputeEngine {

    private final char letter;
    private DataHandler dataHandler;

    private int numRandomMovesChosen; // keeps track of number of random moves it makes
    private int numSmartMovesChosen; // keeps track of number of intelligent moves it makes

    public ComputeEngine(char letter, DataHandler dataHandler){
        this.letter = letter;
        this.dataHandler = dataHandler;

        this.numRandomMovesChosen = 0;
        this.numSmartMovesChosen = 0;
    }

    public int getNumRandomMovesChosen(){
        return this.numRandomMovesChosen;
    }

    public int getNumSmartMovesChosen(){
        return this.numSmartMovesChosen;
    }

    public char getLetter(){
        return this.letter;
    }

    //Calculate next move based on previous boards of the same type
    public int getMove(Board board){

        // generates all possible next moves and the index at which it would play for those moves
        List<Board> nextMoveBoards = new ArrayList<Board>(); // list of all resulting boards for next move
        List<Integer> nextMoveInt = new ArrayList<Integer>(); // list of index played for each of those boards
        generateNextMoveBoards(board, nextMoveBoards, nextMoveInt);

        double largest = -1;
        int nextMoveIndex = -1;
        for(int i = 0; i < nextMoveBoards.size(); ++i){
            double winRate = calculateBoardWinRate(nextMoveBoards.get(i));
            if(winRate > largest){
                largest = winRate;
                nextMoveIndex = i;
            }
        }

        System.out.println("Largest win rate: " + largest);
        System.out.println("Next Move Index: " + nextMoveIndex);

        //if we have little to no data, or if we somehow get some sort of error, pick randomly
        if(nextMoveIndex == -1 || largest <= 0.0001){
            do {
                nextMoveIndex = (int)(Math.random() * 9);
            } while(board.isOccupied(nextMoveIndex));

            System.out.println("Choosing a random location.");

            ++numRandomMovesChosen;
            return nextMoveIndex;
        }

        ++numSmartMovesChosen;
        return nextMoveInt.get(nextMoveIndex);
    }

    //Copy one board to another
    private Board copyBoard(Board board){
        Board copyBoard = new Board();

        for(int i = 0; i < 9; i++){
            try{
                copyBoard.setChar(i, board.getChar(i));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return copyBoard;
    }

    //Generate a list of boards that contain all possible next moves
    private void generateNextMoveBoards(Board board, List<Board> nextMoveBoards, List<Integer> nextMoveInt){

        for (int i = 0; i < 9; i++) {
            if (board.getChar(i) == '-') {
                Board nextMoveBoard = copyBoard(board);
                try{
                    nextMoveBoard.setChar(i, getLetter());
                    nextMoveBoards.add(nextMoveBoard);
                    nextMoveInt.add(i);
                    //System.out.println("added board and index to list");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Calculate the win rate of a board
    private double calculateBoardWinRate (Board board){
        double winRate;

        //System.out.println("getting winning games");
        int numWinningGames = dataHandler.findNumWinningTieGamesByBoardHash(board.toString(), getLetter());
        int allGames = dataHandler.findNumGamesByBoardHash(board.toString());

        List<Board> symmetricBoards = createSymmetricBoards(board);

        String currentBoardHash;
        for(int i = 0; i < symmetricBoards.size(); i++){
            currentBoardHash = symmetricBoards.get(i).toString();

            numWinningGames += dataHandler.findNumWinningTieGamesByBoardHash(currentBoardHash, getLetter());
            allGames += dataHandler.findNumGamesByBoardHash(currentBoardHash);
        }

        if(allGames == 0){
            return -1;
        }
        else{

            winRate = ((double)numWinningGames) / ((double)allGames);
            return winRate;
        }
    }

    private List<Board> createSymmetricBoards(Board board){
        ArrayList<Board> newBoards = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            board = rotateBoardClockWise(board); //"board" variable becomes a rotated version of itself

            if(board == null){
                break;
            }

            newBoards.add(board);//Add this board to the new boards, and repeat, this time you rotate board, it will rotate the already rotated version, thus rotatin it again.
        }

        return newBoards;
    }

    private Board rotateBoardClockWise(Board board){ //Rotates the board clockwise.
        Board resultBoard = new Board();

        char currentSymbol;
        for(int i = 0; i < 3; i++){
            if((currentSymbol = board.getChar(i)) != '-') {
                try {
                    //0 -> 2
                    //1 -> 5
                    //2 -> 8
                    resultBoard.setChar(3 * i + 2, currentSymbol);
                } catch (IllegalMoveException e){
                    e.printStackTrace();
                }
            }
        }

        for(int i = 3; i < 6; i++){
            if((currentSymbol = board.getChar(i)) != '-') {
                try {
                    //3 -> 1
                    //4 -> 4
                    //5 -> 7
                    resultBoard.setChar(3 * i - 8, currentSymbol);
                } catch (IllegalMoveException e){
                    e.printStackTrace();
                }
            }
        }

        for(int i = 6; i < 9; i++){
            if((currentSymbol = board.getChar(i)) != '-') {
                try {
                    //6 -> 0
                    //7 -> 3
                    //8 -> 6
                    resultBoard.setChar(3 * i - 18, currentSymbol);
                } catch (IllegalMoveException e){
                    e.printStackTrace();
                }
            }
        }
        return resultBoard;
    }
}
