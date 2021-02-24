/*
 * Created on 2007 feb 8
 */
package lab4.data;

import lab4.client.GomokuClient;

import java.util.Observable;
import java.util.Observer;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
   private final int DEFAULT_SIZE = 15;
   private GameGrid gameGrid;

   //Possible game states
   private final int NOT_STARTED = 0;
   private final int MY_TURN = 2;
   private final int OTHER_TURN = 3;
   private final int FINISHED = 4;

   private int currentState;

   private GomokuClient client;

   private String message;


   /**
    * The constructor
    *
    * @param gc The client used to communicate with the other player
    */
   public GomokuGameState(GomokuClient gc){
      client = gc;
      client.addObserver(this);
      gc.setGameState(this);
      currentState = NOT_STARTED;
      gameGrid = new GameGrid(DEFAULT_SIZE);
   }


   /**
    * Returns the message string
    *
    * @return the message string
    */
   public String getMessageString()
   {
      return message;
   }

   /**
    * Returns the game grid
    *
    * @return the game grid
    */
   public GameGrid getGameGrid(){
      return gameGrid;
   }

   /**
    * This player makes a move at a specified location
    *
    * @param x the x coordinate
    * @param y the y coordinate
    */
   @SuppressWarnings("deprecation")
public void move(int x, int y)
   {
      if (currentState == MY_TURN){
    	  message = "Hello";
    	  
    	  if(gameGrid.move(x, y, MY_TURN)) 
    	  {  
	         client.sendMoveMessage(x, y);
	         message = "Smart move";
	         System.out.println("x: " + x + "y: " + y);
	         
	         if(gameGrid.isWinner(MY_TURN))
	         {
	        	currentState = FINISHED;
	        	System.out.println("Is winner?");
	        	message = "winner winner chicken dinner!";
	        	setChanged();
	        	notifyObservers();
	        	return;
	         }
	         else {
	        	 currentState = OTHER_TURN;
	        	 setChanged();
	        	 notifyObservers();
	        	 return;
	         }
    	  }
    	  else
    		  message = "Occupide space, select another";
    	  
    	  
      }
      
      
      else if(currentState == NOT_STARTED) {
    	  message = "Not started";
    	  setChanged();
    	  notifyObservers();
    	  return;
      }
      
      else if(currentState == FINISHED) {
    	  message = "Game is finished";
    	  setChanged();
    	  notifyObservers();
    	  return;
      }
      
      else {
    	  message = "Not your turn";
    	  setChanged();
    	  notifyObservers();
      }
   }

   /**
    * Starts a new game with the current client
    */
   public void newGame(){
      if (currentState == MY_TURN){
         gameGrid.clearGrid();
         currentState = OTHER_TURN;
         message = "New Game";
         client.sendNewGameMessage();
         setChanged();
         notifyObservers();
      }
   }

   /**
    * Other player has requested a new game, so the
    * game state is changed accordingly
    */
   public void receivedNewGame(){
      if (currentState == OTHER_TURN)
      {
         gameGrid.clearGrid();
         currentState = MY_TURN;
         message = "New Game+";
         setChanged();
         notifyObservers();
      }
   }

   /**
    * The connection to the other player is lost,
    * so the game is interrupted
    */
   public void otherGuyLeft(){
      gameGrid.clearGrid();
      message = "Lost connection";
      currentState = FINISHED;
      setChanged();
      notifyObservers();
   }

   /**
    * The player disconnects from the client
    */
   public void disconnect(){
      gameGrid.clearGrid();
      currentState = FINISHED;
      message = "Disconnected";
      client.disconnect();
      setChanged();
      notifyObservers();
   }

   /**
    * The player receives a move from the other player
    *
    * @param x The x coordinate of the move
    * @param y The y coordinate of the move
    */
   public void receivedMove(int x, int y){
      gameGrid.move(x, y, OTHER_TURN);
      if (gameGrid.isWinner(OTHER_TURN)){
         message = "Opponent won ;(";
         currentState = FINISHED;
         setChanged();
         notifyObservers();
      }
      else {
         currentState = MY_TURN;
         setChanged();
         notifyObservers();
      }

   }

   public void update(Observable o, Object arg) {

      switch(client.getConnectionStatus()){
         case GomokuClient.CLIENT:
            message = "Game started, it is your turn!";
            currentState = MY_TURN;
            break;
         case GomokuClient.SERVER:
            message = "Game started, waiting for other player...";
            currentState = OTHER_TURN;
            break;
      }
      setChanged();
      notifyObservers();


   }

}