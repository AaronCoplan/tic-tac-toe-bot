package com.tictactoebot;


import static com.tictactoebot.UI.GameStateHandler.createGame;

public class Main {

    public static void main(String[] args){

        createGame(false, false, false);

    }

    public static void test(){
        DataHandler dataHandler = DataHandler.getInstance();
        if(dataHandler == null){
            System.out.println("Error instantiating data handler!");
            System.exit(-1);
        }

        dataHandler.deleteAllMoves();
        dataHandler.deleteAllResults();

        Game game = new Game();

        Board board = new Board();

        try{
            board.setChar(0, 0, 'X');
            game.addMove(board, 0, 0);

            board.setChar(2, 2, 'O');
            game.addMove(board, 2, 2);

            board.setChar(0, 1, 'X');
            game.addMove(board, 0, 1);

            board.setChar(2, 1, 'O');
            game.addMove(board, 2, 1);

            board.setChar(0, 2, 'X');
            game.addMove(board, 0, 2);

        }catch(IllegalMoveException error){
            System.out.println("Error making a move. Test failed.");
        }

        game.setResult('x');


        boolean success = dataHandler.saveGame(game);
        if(success){
            System.out.println("Successfully saved game!");
        }else{
            System.out.println("An error occurred when saving the game!");
        }

        Game g = dataHandler.findGameByGameNumber(1);
        System.out.println(g.getResult());

        List<Game> games = dataHandler.findGamesByBoardHash("XXX----OO");
        System.out.println(games.size());
    }
}