package lab4.gui;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.*;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

import javax.swing.*;

/*
 * The GUI class
 * @author Pontus Eriksson Jirbratt
 * @author Lucas Pettersson
 */

public class GomokuGUI implements Observer{

   private GomokuClient client;
   private GomokuGameState gamestate;
   private GamePanel gameGridPanel;
   private JLabel messageLabel;
   private JButton connectButton;
   private JButton newGameButton;
   private JButton disconnectButton;

   /**
    * The constructor, initializes five components and the JFrame.
    * Five Components: gameGridpanel, messageLabel, connectButton, newGameButton and disconnectbutton. 
    * Creates an Container with a layout. 
    * Adds the components into the Container.
    * Gives them positions relative to each other. 
    *
    * @param g   The game state that the GUI will visualize
    * @param c   The client that is responsible for the communication
    */
   public GomokuGUI(GomokuGameState g, GomokuClient c){
      this.client = c;
      this.gamestate = g;
      client.addObserver(this);
      gamestate.addObserver(this);
      
      SpringLayout layout;
      Container contentPane;
      
      JFrame frame = new JFrame("Gomoku");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLocation(200, 200);
      frame.setSize(500, 500);
      frame.setResizable(false);
      frame.setVisible(true);
      
      layout = new SpringLayout();

      gameGridPanel = new GamePanel(gamestate.getGameGrid());
      messageLabel = new JLabel("Welcome to Gomoku!");
      connectButton = new JButton("Connect");
      newGameButton = new JButton("New Game");
      disconnectButton = new JButton("Disconnect");
      
      //Adding the components to the contentPane (Container)
      contentPane = frame.getContentPane();
      contentPane.setLayout(layout);
      contentPane.add(gameGridPanel);
      contentPane.add(connectButton);
      contentPane.add(newGameButton);
      contentPane.add(disconnectButton);
      contentPane.add(messageLabel);
      
      // Positioning the components with the layout
      layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gameGridPanel, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
      layout.putConstraint(SpringLayout.NORTH, gameGridPanel, 0, SpringLayout.NORTH, contentPane);
      
      layout.putConstraint(SpringLayout.NORTH, connectButton, 20, SpringLayout.SOUTH, gameGridPanel);
      layout.putConstraint(SpringLayout.EAST, connectButton, -10, SpringLayout.WEST, newGameButton);
      
      layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, newGameButton, -10, SpringLayout.HORIZONTAL_CENTER, gameGridPanel);
      layout.putConstraint(SpringLayout.NORTH, newGameButton, 20, SpringLayout.SOUTH, gameGridPanel);
      
      layout.putConstraint(SpringLayout.NORTH, disconnectButton, 20, SpringLayout.SOUTH, gameGridPanel);
      layout.putConstraint(SpringLayout.WEST, disconnectButton, 20, SpringLayout.EAST, newGameButton);
      
     
      layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
      layout.putConstraint(SpringLayout.NORTH, messageLabel, 10, SpringLayout.SOUTH, newGameButton);
      
      
      frame.setContentPane(contentPane);
      
      //Anonymous classes
      
      gameGridPanel.addMouseListener(new MouseAdapter() {
    	  
    	  public void mouseReleased(MouseEvent mouseEvent)  
    	  {
    		  int x = mouseEvent.getX();
    		  int y = mouseEvent.getY();
    		  
    		  int pos[] = gameGridPanel.getGridPosition(x, y);
    		  gamestate.move(pos[1], pos[0]);
    	
    	  }
    	  
      });
   
      connectButton.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ConnectionWindow cw = new ConnectionWindow(client);
		}
    	 
     });
      
      
      newGameButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            gamestate.newGame();
         }
      });
      
      disconnectButton.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            gamestate.disconnect();
         }
      });
   }
  
  
   

   public void update(Observable arg0, Object arg1) {

      // Update the buttons if the connection status has changed
      if(arg0 == client){
         if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
            connectButton.setEnabled(true);
            newGameButton.setEnabled(false);
            disconnectButton.setEnabled(false);
         }else{
            connectButton.setEnabled(false);
            newGameButton.setEnabled(true);
            disconnectButton.setEnabled(true);
         }
      }

      // Update the status text if the gamestate has changed
      if(arg0 == gamestate){
         messageLabel.setText(gamestate.getMessageString());
      }

   }

}