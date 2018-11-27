// Name: Client.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: Basic UDP client with socket. This is modified to send one message
//          to a UDP server, and recieve and parse what returns.


import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;

public class Client
{
    static final int MESSAGES = 10000;
    public static void main(String args[]) throws Exception
    {
        BufferedReader inFromUser =
        new BufferedReader(new InputStreamReader(System.in));


        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("165.227.159.60");

        byte[] sendData = new byte[30];
        byte[] receiveData = new byte[30];
        long[] order = new long[100];
        String sentence = inFromUser.readLine();

        sendData = sentence.getBytes(StandardCharsets.US_ASCII);
        clientSocket.setSoTimeout(80000);

        DatagramPacket sendPacket = 
        new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
         new DatagramPacket(receiveData, receiveData.length);
        int index = 0; 
        try
        {

            while(true) 
            {
                clientSocket.receive(receivePacket);

                String modifiedSentence = new String(receivePacket.getData());
    	           //order[i] = Long.parseLong(modifiedSentence.trim());
                String[] split = modifiedSentence.split("x");
                int parsed = Integer.parseInt(split[0]);

                if(parsed == -1) // check for "finished" message.
                {
                    System.out.println("Transmission complete.");
                    break;
                }

                order[index] = parsed;
                System.out.println(order[index]);

                System.out.println("FROM SERVER:" + modifiedSentence);
                index = index + 1;
            }
        } catch (SocketException e)
          {
              System.err.println("Timed out after 60 seconds..." +
                " Probably your first message got lost: " + e);
                clientSocket.close();
                System.exit(1);    
          }
          for (int j = 0; j < 100; j++){
            if (!(order[j] == j)){
                System.out.println(j + " out of order!");
                break;
            }
          }
        System.out.println("Final results: " + Arrays.toString(order));
        System.out.println("Array length: " + order.length);
        System.out.println("Percent arrived: " + order.length / MESSAGES * 100 + "%");
        clientSocket.close(); 
    }
}
