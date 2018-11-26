import java.net.*;
import java.util.Random;


class Server
{
   public static void main(String args[]) throws Exception
      {
         DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[30];
            byte[] sendData = new byte[30];
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
                  for(int i = 0; i < 10; i++)
                  {
                	  String fill = Integer.toString(count) + "x";
                	  for(int j = 30; j > String.valueOf(fill).length(); j --)
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
               }
      }
}