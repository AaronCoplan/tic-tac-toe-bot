package com.tictactoebot.computeEngine;

import com.tictactoebot.dataHandler.DataHandler;
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

        //QUESTION: won't this if statement only get activated if the board is full? in which case the GameStateHandler will have taken care of things anyways?
        /*if(nextMoveBoards.size() == 0 || nextMoveInt.size() == 0){
            int location;

            do {
                location = (int)(Math.random() * 9);
            } while(board.isOccupied(location));

            return location;
        }*/

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
        if(nextMoveIndex == -1 || largest == -1 || largest == 0){
            do {
                nextMoveIndex = (int)(Math.random() * 9);
            } while(board.isOccupied(nextMoveIndex));

            System.out.println("Choosing a random location.");

            ++numRandomMovesChosen;
            return nextMoveIndex;
        }

        /*while(currentIndex != nextMoveBoards.size() - 1){
            double largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex));
            if(nextMoveBoards.size() == 1 || calculateBoardWinRate(nextMoveBoards.get(currentIndex+1)) > largest){
                largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex+1));
                currentIndex = currentIndex + 1;
                //System.out.println("if loop");
            }else{
                currentIndex = currentIndex + 1;
                //System.out.println("Else loop");
            }
        }*/

        //System.out.println(nextMoveBoards.size());
        //System.out.println(nextMoveInt.size());
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
        List<Game> winningGames = dataHandler.findWinningGamesByBoardHash(board.toString(), getLetter());
        //System.out.println("getting all games");
        List<Game> allGames = dataHandler.findGamesByBoardHash(board.toString());

        if(allGames.size() == 0){
            return -1;
        }
        else{
            winRate = (((double)winningGames.size()) / ((double)allGames.size()));
            return winRate;
        }
    }

}
