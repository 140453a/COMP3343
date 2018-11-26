import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client
{
   static final int MESSAGES = 10000;
   public static void main(String args[]) throws Exception
   {
      BufferedReader inFromUser =
         new BufferedReader(new InputStreamReader(System.in));
      
      
      DatagramSocket clientSocket = new DatagramSocket();
      
      InetAddress IPAddress = InetAddress.getByName("localhost");
      
      byte[] sendData = new byte[30];
      byte[] receiveData = new byte[30];
      long[] order = new long[10000];
      String sentence = inFromUser.readLine();
      
      sendData = sentence.getBytes();
      
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);


      for(int i = 0; i < MESSAGES; i++) {
    	  clientSocket.receive(receivePacket);
      
    	  String modifiedSentence = new String(receivePacket.getData());
    	  //order[i] = Long.parseLong(modifiedSentence.trim());
    	  String[] split = modifiedSentence.split("x");
    	  order[i] = Integer.parseInt(split[0]);
    	  System.out.println(order[i]);
    		  
    	  System.out.println("FROM SERVER:" + modifiedSentence);

      }
      System.out.println("Final results: " + Arrays.toString(order));
      clientSocket.close(); 
   }
}

// NOTE: You should just keep server and client barebones, and move all the testing to a new test-class. Keep the x0000 padding in server,
// but move other things to new class :)
