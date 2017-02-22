package com.tictactoebot.dataHandler.dao;

import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.model.Move;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by afcoplan on 2/22/17.
 */
public class GameDao {

    private static final String dbName = "tttb";
    private static final String username = "root";
    private static final String password = "164895Cope$";
    private static final String params = "?useSSL=false";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/" + dbName + params;

    public static List<Game> fetchAllGames(){
        List<Game> games = new ArrayList<Game>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(dbUrl, username, password);

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games");

            while(rs.next()){
                Game game = new Game();

                game.setGameNumber(rs.getInt("game_id"));
                game.setResult(rs.getString("result").charAt(0));
                game.setOpponentType(rs.getString("opponent_type"));

                List<Move> moves = new ArrayList<Move>();

                String q2 = "SELECT * FROM moves WHERE game_id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(q2);
                preparedStatement.setInt(1, rs.getInt("game_id"));

                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    int moveNumber = resultSet.getInt("move_number");
                    int indexPlayed = resultSet.getInt("index_played");
                    String boardHash = resultSet.getString("board_hash");
                    Move move = new Move(moveNumber, boardHash, indexPlayed);

                    moves.add(move);
                }

                game.setMoves(moves);

                games.add(game);
            }

            stmt.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return games;
    }

}
