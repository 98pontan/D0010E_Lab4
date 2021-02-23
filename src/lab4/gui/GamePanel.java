package lab4.gui;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Observer{

   private final int UNIT_SIZE = 20;
   private GameGrid grid;

   /**
    * The constructor
    *
    * @param grid The grid that is to be displayed
    */
   public GamePanel(GameGrid grid){
      this.grid = grid;
      grid.addObserver(this);
      Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
      this.setMinimumSize(d);
      this.setPreferredSize(d);
      this.setBackground(Color.WHITE);
   }

   /**
    * Returns a grid position given pixel coordinates
    * of the panel
    *
    * @param x the x coordinates
    * @param y the y coordinates
    * @return an integer array containing the [x, y] grid position
    */
   public int[] getGridPosition(int x, int y){
      int gridPos[];

      gridPos = new int[2];
      gridPos[0]= x / UNIT_SIZE;
      gridPos[1] = y / UNIT_SIZE;

      return gridPos;
   }

   /**
    * Each time observers are notify update will be called
    * @param arg0
    * @param arg1
    */

   public void update(Observable arg0, Object arg1) {
      this.repaint();
   }

   /**
    * Paints the game grid
    * @param g
    */

   public void paintComponent(Graphics g){
      super.paintComponent(g);
      Graphics2D graphics2D = (Graphics2D) g;
      Stroke regularStroke = graphics2D.getStroke();
      int row;
      int column;
      int padding = 2;
      for (int i = 0; i < grid.getSize(); i++)
      {
         for (int j = 0; j < grid.getSize(); j++)
         {
            row = j * UNIT_SIZE;
            column = i * UNIT_SIZE;
            g.drawRect(row, column, UNIT_SIZE, UNIT_SIZE);

            if (grid.getLocation(i, j) == grid.ME){
               g.setColor(Color.red);
               g.fillOval(row, column, UNIT_SIZE - 1, UNIT_SIZE- 1);
               g.setColor(Color.BLACK);
               //System.out.println("x: " + column + "y " + row);
            }

            else if(grid.getLocation(i, j) == grid.OTHER){
               graphics2D.setColor(Color.BLACK);
               graphics2D.setStroke(new BasicStroke(2));
               g.setColor(Color.BLACK);
               g.fillOval(row, column, UNIT_SIZE - 1, UNIT_SIZE- 1);
               
              // graphics2D.drawLine(column + padding, row + padding, column + UNIT_SIZE - padding, row + UNIT_SIZE - padding);
              // graphics2D.drawLine(column, row - UNIT_SIZE + padding, column - UNIT_SIZE - padding, row + padding);
              // graphics2D.setStroke(regularStroke);
            }
         }
      }

   }

}