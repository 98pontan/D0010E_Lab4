package lab4;


import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain
{
   public static void main(String[] args)
   {
	  GomokuClient client;
      GomokuGameState state;
      int portNumber;

      if (args.length == 1)
         portNumber = Integer.parseInt(args[0]);

      else
         portNumber = 5400;

      client = new GomokuClient(portNumber);
      state = new GomokuGameState(client);
      new GomokuGUI(state, client);
   }
}
