// Name: Server.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: This class sets up a Datagram Socket to receive packets from the client.
//          The packets are then written to a file on the server.

import java.io.*;
import java.net.*;

class UDPServer
{
    static final int PORT_NUMBER = 9876;
    static final int BYTE_SIZE = 1024;
    public static void main(String args[]) throws IOException
    {
        // Server socket is initialized with previously agreed-upon port number
        DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);
        byte[] Data = new byte[BYTE_SIZE];
        int count = 0;
        
        // Setting up File IO 
        FileWriter file = new FileWriter("testResults.txt");
        PrintWriter out = new PrintWriter(file);

        // The loop that receives all the packets from  the client
        while(true)
        {
            // Creating the receiving packet which the socket will fill with the received data
            DatagramPacket recPacket = new DatagramPacket(Data, Data.length);
            serverSocket.receive(recPacket);
            
            System.out.println("\n Packet length: " + recPacket.getLength());
            
            String modifiedSentence = new String(recPacket.getData());
            out.write(modifiedSentence, 0, recPacket.getLength());
            
            System.out.println("\nPacket " + ++count + " written to file\n");
            out.flush();
        }
    }
}
