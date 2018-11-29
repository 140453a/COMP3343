
// Name: UDPClient.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: This class sets up a UDP socket, converts a text file into bytes, then makes a datagram
//          which is sent to the server in UDPServer.

import java.io.*;
import java.net.*;
import java.util.Arrays;

class UDPClient
{
    static final int serverPort = 9876;
    static final int MAX_SIZE = 32;
    public static void main(String args[]) throws SocketException, IOException
    {
        int count = 0;

        // Setting up the datagram socket. This does not need a port number because it will not
        // receive any responses from the server.
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IpAddress = InetAddress.getByName("165.227.159.60");

        byte[] sendData = new byte[MAX_SIZE];

        // Setting up File IO
        String filePath = "testSend.txt";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        // Loops over the text file in chunks of size MAX_SIZE and counts them
        int fileLength = 0; 
        while((count = fis.read(sendData)) != -1)
        {
            fileLength += count;
        }

        System.out.println("Total Length :" + fileLength);

        // Calculates the number of packets needed to send the file
        int packetCount = fileLength / MAX_SIZE;
        System.out.println("Number of packets : " + packetCount);

        // The offset is the first byte of the last packet
        int offset = packetCount * MAX_SIZE;  
        
        // The last packet is the only packet that is not MAX_SIZE in length. Instead of filling
        // the rest of the packet with 0's, we simply send a smaller packet size (if the total size
        // is not a multiple of MAX_SIZE)
        int lastPackLen = fileLength - offset;
        System.out.println("\nLast packet Length : " + lastPackLen);

        // Create new array for the last packet
        byte[] lastPack = new byte[lastPackLen-1];


        fis.close();

        FileInputStream fis1 = new FileInputStream(file);

        
        while((count = fis1.read(sendData)) != -1 )
        { 
            if(packetCount <= 0)
            {
                break;
            }

            System.out.println(new String(sendData)); // The contents of the packet
            
            // Setting up the actual packet with all relevant information to be sent through the socket
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, serverPort);
            
            // The packet is sent to the IP Address and port given in the packet above
            clientSocket.send(sendPacket);
            System.out.println("========");
            System.out.println("Last pack sent " + sendPacket);
            packetCount--;
        }

        //check
        System.out.println("\nlast packet\n");
        System.out.println(new String(sendData));

        lastPack = Arrays.copyOf(sendData, lastPackLen);

        System.out.println("\nActual last packet\n");
        System.out.println(new String(lastPack));
                //send the correct packet now. but this packet is not being send. 
        DatagramPacket sendPacket1 = new DatagramPacket(lastPack, lastPack.length, IpAddress, serverPort);
        clientSocket.send(sendPacket1);
        System.out.println("last pack sent" + sendPacket1);

    }
}
