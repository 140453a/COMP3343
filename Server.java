import java.net.*;
import java.util.Random;


public class Server
{
    static final int MESSAGES = 10000;
    static final int BYTE_SIZE = 30;
    static final int PORT_NUMBER = 9876;
   public static void main(String args[]) throws Exception
      {
         DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);
            byte[] receiveData = new byte[BYTE_SIZE];
            byte[] sendData = new byte[BYTE_SIZE];
            int count = 1;
            Random rand = new Random();
            
            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  
                  String sentence = new String( receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence);
                  
                  InetAddress IPAddress = receivePacket.getAddress();
                  
                  int port = receivePacket.getPort();
                  
                  //String capitalizedSentence = sentence.toUpperCase();
                  //sendData = capitalizedSentence.getBytes();
                  for(int i = 0; i < MESSAGES; i++)
                  {
                	  String fill = Integer.toString(count) + "x";
                	  for(int j = BYTE_SIZE; j > String.valueOf(fill).length(); j --)
                	  {
                		  fill = fill + "0";
                	  }
                	  sendData =  fill.getBytes();
                	  DatagramPacket sendPacket =
                			  new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  
                	  Thread.sleep(2);
                	  serverSocket.send(sendPacket);
                	  count = count + 1;
                  }
                  serverSocket.close();  
                  System.exit(0);                  
               }             
      }
}