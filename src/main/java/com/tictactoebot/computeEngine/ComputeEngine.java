package com.tictactoebot.computeEngine;

import java.util.List;
import com.tictactoebot.dataHandler.model.Board;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.DataHandlerImpl;
import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.gameEngine.handlers.GameStateHandler;

import java.util.ArrayList;

/**
 * Created by Devin on 2/14/2017.
 */
public class ComputeEngine {

    private final char letter;
    private DataHandler dataHandler;
    private List<Board> nextMoveBoards = new ArrayList<Board>();
    private List<Integer> nextMoveInt = new ArrayList<Integer>();

    public ComputeEngine(char letter, DataHandler dataHandler){
        this.letter = letter;
        this.dataHandler = dataHandler;
    }

    public char getLetter(){
        return this.letter;
    }

    //Calculate next move based on previous boards of the same type
    public int getMove(Board board){
        int currentIndex = 0;

        nextMoveBoards.clear();
        nextMoveInt.clear();
        generateNextMoveBoards(board);

        if(nextMoveBoards.size() == 0){
            int location;

            do {
                location = (int)(Math.random() * 9);
            } while(board.isOccupied(location));

            System.out.println("returning random location");
            return location;
        }

        while(currentIndex != nextMoveBoards.size() - 1){
            double largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex));
            if(nextMoveBoards.size() == 1 || calculateBoardWinRate(nextMoveBoards.get(currentIndex+1)) > largest){
                largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex+1));
                currentIndex = currentIndex + 1;
                System.out.println("if loop");
            }else{
                currentIndex = currentIndex + 1;
                System.out.println("Else loop");
            }
        }

        System.out.println(nextMoveBoards.size());
        System.out.println(nextMoveInt.size());
        return nextMoveInt.get(currentIndex -1);
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
    private void generateNextMoveBoards(Board board){
        System.out.println("made it loop");
        for (int i = 0; i < 9; i++) {
            if (board.getChar(i) == '-') {
                Board nextMoveBoard = copyBoard(board);
                try{
                    nextMoveBoard.setChar(i, getLetter());
                    nextMoveBoards.add(nextMoveBoard);
                    nextMoveInt.add(i);
                    System.out.println("added board and index to list");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Calculate the win rate of a board
    private double calculateBoardWinRate (Board board){
        double winRate;

        System.out.println("getting winning games");
        List<Game> winningGames = dataHandler.findWinningGamesByBoardHash(board.toString(), getLetter());
        System.out.println("getting all games");
        List<Game> allGames = dataHandler.findGamesByBoardHash(board.toString());

        if(allGames.size() == 0){
            return 0;
        }
        else{
            winRate = (winningGames.size() / (allGames.size()));
            return winRate;
        }
    }

}
