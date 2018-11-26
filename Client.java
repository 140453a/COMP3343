import java.io.*;
import java.net.*;

class Client
{
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
      
      for(int i = 0; i < 10; i++) {
    	  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	  clientSocket.receive(receivePacket);
      
    	  String modifiedSentence = new String(receivePacket.getData());
    	  //order[i] = Long.parseLong(modifiedSentence.trim());
    	  String[] split = modifiedSentence.split("x");
    	  order[i] = Integer.parseInt(split[0]);
    	  System.out.println(order[i]);
    		  
    	  System.out.println("FROM SERVER:" + modifiedSentence);
      }
      System.out.println("Final results: " + order.toString());
      clientSocket.close(); 
   }
}

// NOTE: You should just keep server and client barebones, and move all the testing to a new test-class. Keep the x0000 padding in server,
// but move other things to new class :)
