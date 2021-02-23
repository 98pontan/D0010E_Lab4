package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable
{
   public static final int EMPTY = 1;
   public static final int ME = 2;
   public static final int OTHER = 3;

   private final int INROW = 5;
   int[][] gameGridList; //might be private check later
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
      System.out.println("Inside move GameGrid");

      if (player == OTHER) {
    	  System.out.println("Other Player");
         return false;
      }

      else if (gameGridList[x][y] != EMPTY) {
    	  System.out.println("Not empty");
         return false;
      }

      else
      {
         gameGridList[x][y] = player;
         setChanged();
         notifyObservers();
         System.out.println("true");
         return true;
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
    * checks 6 instead of 5 right now, unnecessary but wont effect the program
    * @param player the player to check for
    * @return true if player has 5 in row, false otherwise
    */
   public boolean isWinner(int player)
   {
      int down = 10;
      int x;
      int y = 0;

      int directions = 8;
      int flagWinner = 0;
      int offset;

      for (int i = 0; i < directions * INROW; i++)
      {

         if (y >= gameGridList.length)
            continue;
         //look up
         if (i < 5)
         {
            if (gameGridList[xLastPos][yLastPos + i] == player)
            {
               flagWinner++;
               continue;
            }
            else
               i = 5;

         }

         //look down
         else if (i < 10)
         {
            offset = down - 5;

            y = yLastPos - (i - offset);
            if (y < 0)
               continue;

            if (gameGridList[xLastPos][yLastPos - (i - offset)] == player)
            {
               flagWinner++;
               continue;
            }

            else
            {
               i = 10;
            }
         }

         //See if the pieces above and below are 5 in a row
         if (i == 10)
         {
            if (flagWinner >= INROW)
               return true;

            flagWinner = 0;
         }

         //look to the right
         else if (i < 15 && i >= 10)
         {
            offset = 15 - 5;
            x = xLastPos + (i - offset);
            System.out.println("x: " + x + "i: " + i);
            if (x >= gameGridList.length)
               continue;

            if (gameGridList[xLastPos + (i - offset)][yLastPos] == player)
            {
               flagWinner++;
               continue;
            } else
               i = 15;
         }

         //look to the left
         else if (i < 20)
         {
            offset = 20 - 5;

            x = xLastPos - (i - offset);
            if (x < 0)
               continue;

            if (gameGridList[xLastPos - (i - offset)][yLastPos] == player)
            {
               flagWinner++;
               continue;
            } else
               i = 20;
         }

         //Checks if the right and left pieces are 5 in a row
         if (i == 20)
         {
            if (flagWinner >= INROW)
               return true;
            flagWinner = 0;
         }

         //look down a cross to the left
         if (i < 25)
         {
            offset = 25 - 5;
            x = xLastPos - (i - offset);
            y = yLastPos - (i - offset);
            if (x < 0|| y < 0)
               continue;

            if (gameGridList[xLastPos - (i - offset)][yLastPos - (i - offset)] == player)
            {
               flagWinner++;
               continue;
            } else
               i = 25;
         }

         //look up across to the right
         else if (i < 30)
         {
            offset = 30 - 5;

            x = xLastPos + (i - offset);
            y = yLastPos + (i - offset);
            if (x >= gameGridList.length || y >= gameGridList.length)
               continue;

            if (gameGridList[xLastPos + (i - offset)][yLastPos + (i - offset)] == player)
            {
               flagWinner++;
               continue;
            } else
               i = 30;
         }

         //Check if down across to the left and up across to the right is five in a row
         if (i == 30)
         {
            if (flagWinner >= INROW)
               return true;
            flagWinner = 0;
         }

         //Look down across to the right
         else if (i < 35 && i >= 30)
         {
            offset = 35 - 5;
            x = xLastPos + (i - offset);
            y = yLastPos - (i - offset);

            if (x >= gameGridList.length || y < 0)
               continue;

            if (gameGridList[xLastPos + (i - offset)][yLastPos - (i - offset)] == player)
               flagWinner++;

            else
               i = 35;
         }

         //look up across to the left
         else if (i < 40 && i >= 35)
         {
            offset = 40 - 5;
            x = xLastPos - (i - offset);
            y = yLastPos + (i - offset);
            if (x < 0 || y >= gameGridList.length)
               continue;

            if (gameGridList[xLastPos - (i - offset)][yLastPos + (i - offset)] == player)
               flagWinner++;

            else if (flagWinner >= INROW)
               return true;

         }
      }
      return false;
   }


}