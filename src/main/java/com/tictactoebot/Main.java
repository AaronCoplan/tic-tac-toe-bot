package com.tictactoebot;


import com.tictactoebot.dataHandler.DataHandler;
import com.tictactoebot.dataHandler.dao.GameDao;
import com.tictactoebot.dataHandler.model.Game;
import com.tictactoebot.dataHandler.model.Move;
import com.tictactoebot.gameEngine.ConnectionInfo;
import com.tictactoebot.gameEngine.GameEngine;
import com.tictactoebot.gameEngine.Options;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args){

        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Main.class);
        springApplicationBuilder.headless(false);

        springApplicationBuilder.run(args);

    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            List<Game> games = GameDao.fetchAllGames();
            for(Game g : games){
                System.out.println(g.getOpponentType());
                for(Move m : g.getMoves()){
                    System.out.println(m);
                }
            }

            DataHandler dataHandler = DataHandler.getInstance();

            addShutdownHook(dataHandler);

            Options options = getOptions(args);

            long startTime = System.nanoTime();

            GameEngine gameEngine = new GameEngine(dataHandler, options);

            gameEngine.run();
            System.out.println((System.nanoTime() - startTime)/1000000000.0);

        };
    }


    public static Options getOptions(String[] args){
        Options options = new Options();

        boolean randomTrainer = false; // defaults to false
        int numTrainingGames = -1;

        // prints usage information
        if(args.length == 1 && args[0].equals("help")){
            System.out.println("Usage information:");
            System.out.println("To play against the computer: java -jar build/libs/tictactoebot-v0.0.1.jar");
            System.out.println("To train the bot: java -jar build/libs/tictactoebot-v0.0.1.jar train <num games>");
            System.out.println("To host BotVsBot training: java -jar build/libs/tictactoebot-v0.0.1.jar host <port>");
            System.out.println("    NOTE: To exit from host, type `exit` in console then press enter, or type `ctrl-c`");
            System.out.println("To be a client in BotVsBot training: java -jar build/libs/tictactoebot-v0.0.1.jar client <ip> <port> <num games>");
            System.exit(0);
        }

        // activates training mode
        if(args.length == 2 && args[0].equals("train")){
            randomTrainer = true;
            numTrainingGames = Integer.parseInt(args[1]);
        }

        //activates bot vs bot training mode
        if(args.length == 2 && args[0].equals("host")){
            options.connectionInfo = new ConnectionInfo(Integer.parseInt(args[1]));
        }

        if(args.length == 4 && args[0].equals("client")){
            options.connectionInfo = new ConnectionInfo(args[1], Integer.parseInt(args[2]));
            options.setNumTrainingGames(Integer.parseInt(args[3]));
        }

        options.setRandomTrainerOn(randomTrainer);
        if(randomTrainer){
            options.setNumTrainingGames(numTrainingGames);
        }
        options.setMillisBetweenGames(3000); // 4 second delay between games

        return options;
    }


    public static void addShutdownHook(DataHandler dataHandler){
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                // this code executes on system exit
                System.out.println("\nShutdown Hook Activated...");



                // do some things here

                //TODO: WRITE GAME NUM TO FILE

                /*
                 *  Pseudocode:
                 *
                 *  delete stats file (method call to data handler)
                 *  create a new stats file (another call to data handler)
                 */
                dataHandler.resetStats(); // this deletes the existing (if any) stats file
                dataHandler.writeStats(); // resaves the stats

                System.out.println("Shutdown Hook Executed Successfully.");
            }
        }));
    }
}