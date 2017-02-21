public class HostEngine {
   private ServerSocket srv;
   private Socket client;
   private DataHandler dataHandler;
   private final char[] letters = {'X', 'O'};

   public HostEngine(int port, int numGames, DataHandler datahandler){
      srv = new ServerSocket(port);
      this.dataHandler = dataHandler;
      
      client = srv.accept(); //Wait for the client to connect and create a socket
      gameLoop(numGames);
   }
   
   private gameLoop(int numGames){
      for (int i = 0; i < numGames; i++){
         //Decide who goes first
         int thisLetterIndex = ((int)Math.random() * 2);
         int clientLetterIndex = (thisLetterIndex == 0) ? 1 : 0);
         
        
         //Wait for first move/ Go first
         //Wait for second move/ move
      
      
      }
   }
}