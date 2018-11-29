// Name: Server.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: This class sets up a Datagram Socket to receive packets from the client.
//          The packets are then written to a file on the server.

import java.io.*;
import java.net.*;
import java.util.Arrays;

class UDPServer
{
    static final int PORT_NUMBER = 9876;
    static final int BYTE_SIZE = 32;
    public static void main(String args[]) throws IOException
    {
        // Server socket is initialized with previously agreed-upon port number
        DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);

        // Sets the server to time out after 40 seconds, stopping loop.
        serverSocket.setSoTimeout(40000);

        byte[] Data = new byte[BYTE_SIZE];
        int count = 0;
        
        // Setting up File IO 
        FileOutputStream fos = new FileOutputStream("testResults.txt");

        // The loop that receives all the packets from  the client
        while(true)
        {
            // Creating the receiving packet which the socket will fill with the received data
            DatagramPacket recPacket = new DatagramPacket(Data, Data.length);

            // Times out after 40 seconds, ending the program
            try 
            {
                serverSocket.receive(recPacket);
            } catch (SocketTimeoutException e) {
                System.err.println("Timed out, the client is done sending.");
                serverSocket.close();
                break;
            }

            
            System.out.println("\n Packet length: " + recPacket.getLength());
            try
            { 
            // Converting the byte array to a string to print it.
            String modifiedSentence = new String(recPacket.getData());
            System.out.println(modifiedSentence.substring(0, recPacket.getLength()));

            // Writing the data to the file
            fos.write(recPacket.getData(), 0, recPacket.getLength());
            } catch (StringIndexOutOfBoundsException e)
            {
                ; // The packet was empty
            }
            //Flushing old data
            fos.flush(); 
            System.out.println("\nPacket " + ++count + " written to file\n");
            

        }
    }
}
