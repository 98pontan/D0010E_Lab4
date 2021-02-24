package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 * @author Pontus Eriksson Jirbratt
 * @author Lucas Pettersson
 */

public class GameGrid extends Observable
{
   public static final int EMPTY = 1;
   public static final int ME = 2;
   public static final int OTHER = 3;

   private final int INROW = 2;
   private int[][] gameGridList; 
   private int xLastPos;
   private int yLastPos;

   /**
    * Constructor
    *
    * @param size The width/height of the game grid
    */
   public GameGrid(int size)
   {
      gameGridList = new int[size][size];
      for (int i = 0; i < gameGridList.length; i++)
      {
         for (int j = 0; j < gameGridList.length; j++)
         {
            gameGridList[i][j] = 1;
         }
      }
   }

   /**
    * Reads a location of the grid
    *
    * @param x The x coordinate
    * @param y The y coordinate
    * @return the value of the specified location
    */
   public int getLocation(int x, int y)
   {
      return gameGridList[x][y];
   }

   /**
    * Returns the size of the grid
    *
    * @return the grid size
    */
   public int getSize()
   {
      return gameGridList.length;
   }

   /**
    * Enters a move in the game grid
    *
    * @param x      the x position
    * @param y      the y position
    * @param player
    * @return true if the insertion worked, false otherwise
    */
   public boolean move(int x, int y, int player)
   {
      xLastPos = x;
      yLastPos = y;
      System.out.println("Inside move GameGrid, x:" + x + "y: " + y);

     if (gameGridList[x][y] == EMPTY) {
    	 gameGridList[x][y] = player;
         setChanged();
         notifyObservers();
         System.out.println("true");
         return true;
      }

      else
      {
        return false;
      }
   }

   /**
    * Clears the grid of pieces
    */
   public void clearGrid()
   {
      for (int i = 0; i < gameGridList.length; i++)
      {
         for (int j = 0; j < gameGridList.length; j++)
         {
            gameGridList[i][j] = 1;
         }
      }
      setChanged();
      notifyObservers();
   }

   /**
    * Check if a player has 5 in row
    * @param player the player to check for
    * @return true if player has 5 in row, false otherwise
    */
   
   public boolean isWinner(int player)
   {
	   int flagWinner = 0;
	   int xCheck = 0;
	   int yCheck = 0;
	   
	   //Right
	   for(int i = 0; i < INROW; i++) 
	   {
		   yCheck = yLastPos + i;
		   
		   if(yCheck >= gameGridList.length)
			   break;
		   
		   if (gameGridList[xLastPos][yCheck] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Left
	   for(int i = 1; i < INROW; i++) 
	   {
		   yCheck = yLastPos - i;
		   
		   if(yCheck < 0)
			   break;
		   
		   if (gameGridList[xLastPos][yCheck] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Check Left and Right
	   if(flagWinner >= INROW) {
		   System.out.println("Up and Down: " + flagWinner);
		   return true;
	   }
	   
	   else
		   flagWinner = 0;
	   
	   
	   //Up
	   for(int i = 0; i < INROW; i++) 
	   {
		   xCheck = xLastPos + i;
		   if(xCheck >= gameGridList.length)
			   break;
		   
		   if (gameGridList[xLastPos + i][yLastPos] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Down
	   for(int i = 1; i < INROW; i++) 
	   {
		   xCheck = xLastPos - i;
		   if(xCheck < 0)
			   break;
		   
		   if (gameGridList[xLastPos - i][yLastPos] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Check Up and Down
	   if(flagWinner >= INROW) {
		   System.out.println("Left and Right: " + flagWinner);
		   return true;
	   }
	   
	   else
		   flagWinner = 0;
	   
	  
	   //Up Right
	   for(int i = 0; i < INROW; i++) 
	   {
		   xCheck = xLastPos - i;
		   yCheck = yLastPos + i;
		   
		   if(xCheck < 0 || yCheck >= gameGridList.length)
			   break;
		   
		   if (gameGridList[xLastPos - i][yLastPos + i] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Down Left
	   for(int i = 1; i < INROW; i++) 
	   {
		   xCheck = xLastPos + i;
		   yCheck = yLastPos - i;
		   
		   if(yCheck < 0 || xCheck >= gameGridList.length)
			   break;
		   
		   if (gameGridList[xLastPos + i][yLastPos - i] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Check Up right and Down left
	   if(flagWinner >= INROW) {
		   return true;
	   }
	   
	   else
		   flagWinner = 0;
	   
	   
	   //Up left
	   for(int i = 0; i < INROW; i++) 
	   {
		   xCheck = xLastPos - i;
		   yCheck = yLastPos - i;
		   
		   if(xCheck < 0 || yCheck < 0)
			   break;
		   
		   if (gameGridList[xLastPos - i][yLastPos - i] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Down right
	   for(int i = 1; i < INROW; i++) 
	   {
		   xCheck = xLastPos + i;
		   yCheck = yLastPos + i;
		   
		   if(xCheck >= gameGridList.length || yCheck >= gameGridList.length)
			   break;
		   
		   if (gameGridList[xLastPos + i][yLastPos + i] == player)
           {
              flagWinner++;
              continue;
           }
	   }
	   
	   //Up left and Down right
	   if(flagWinner >= INROW) {
		   System.out.println("Up Left and Down right");
		   return true;
	   }
	   
	   else
		   flagWinner = 0;
	   
	   
	   return false;
   }

}