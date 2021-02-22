package lab4.gui;
import java.awt.Container;
import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

import javax.swing.*;

/*
 * The GUI class
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
    * The constructor
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
      frame.setVisible(true);
      
      layout = new SpringLayout();

      gameGridPanel = new GamePanel(gamestate.getGameGrid());
      messageLabel = new JLabel();
      connectButton = new JButton("Connect");
      newGameButton = new JButton("New Game");
      disconnectButton = new JButton("Disconnect");
      
      contentPane = frame.getContentPane();
      contentPane.setLayout(layout);
      contentPane.add(gameGridPanel);
      contentPane.add(connectButton);
      contentPane.add(newGameButton);
      contentPane.add(disconnectButton);
      contentPane.add(messageLabel);
      
      layout.putConstraint(SpringLayout.WEST, gameGridPanel, 5, SpringLayout.WEST, contentPane);
     
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