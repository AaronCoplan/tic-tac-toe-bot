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
        generateNextMoveBoards(board);
        while(currentIndex != nextMoveBoards.size()){
            double largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex));
            if(calculateBoardWinRate(nextMoveBoards.get(currentIndex+1)) > largest){
                largest = calculateBoardWinRate(nextMoveBoards.get(currentIndex+1));
                currentIndex = currentIndex + 1;
            }else{
                currentIndex = currentIndex + 1;
            }
        }
        return nextMoveInt.get(currentIndex);
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
        Game game = GameStateHandler.getGame();
        for(int i = 0; i < 9; i++){
            if(board.getChar(i) == '-'){
                Board nextMoveBoard = copyBoard(board);
                try{
                    game.addMove(nextMoveBoard, i);
                }catch(Exception e){
                    e.printStackTrace();
                }
                nextMoveBoards.add(nextMoveBoard);
                nextMoveInt.add(i);
            }
        }
    }

    //Calculate the win rate of a board
    private double calculateBoardWinRate (Board board){
        double winRate;

        List<Game> winningGames = dataHandler.findWinningGamesByBoardHash(board.toString(), getLetter());
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
