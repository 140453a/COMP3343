
// Name: UDPClient.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: This class sets up a UDP socket, converts a text file into bytes,
// then makes a datagram which is sent to the server in UDPServer.

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

class UDPClient
{
    static final int serverPort = 9876;
    static final int MAX_SIZE = 32;
    public static void main(String args[]) throws SocketException, IOException
    {
        // Variable to count length of file
        int count = 0;


        // Setting up the datagram socket. This does not need a port number 
        // because it will not receive any responses from the server.
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IpAddress = InetAddress.getByName("165.227.159.60");


        // This is the byte array that will be filled and sent off repeatedly
        // by the DatagramPacket and socket.
        byte[] sendData = new byte[MAX_SIZE];

        // Setting up File IO
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of your file: ");
        String filePath = scan.nextLine();

        File file = new File(filePath);
       

        // Using a FIS to read file in byte chunks
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
        System.out.println("Number of packets (not including remainder) : " +
                            packetCount);



        // The offset is the -1st byte of the last packet
        int offset = packetCount * MAX_SIZE;  
        
        // The last packet is the only packet that is not MAX_SIZE in length.
        // Instead of filling the rest of the packet with 0's, we simply send a 
        // smaller packet size (if the total size is not a multiple of MAX_SIZE)
        int lastPackLen = fileLength - offset;
        System.out.println("\nLast packet Length : " + lastPackLen + "\n");


        // Logic that will be used at the end to see if we need to send a last
        // packet that is smaller than normal
        boolean remainder = true; 
        if(lastPackLen == 0)
        {
            remainder = false;
        }


        fis.close();

        FileInputStream fis1 = new FileInputStream(file);

        
        while((count = fis1.read(sendData)) != -1)
        { 
            if(packetCount <= 0)
            {
                break;
            }
            System.out.println("Packet number: " + packetCount);
            System.out.println(new String(sendData));
            
            // Setting up the actual packet with all relevant information to be
            // sent through the socket
            DatagramPacket sendPacket = new DatagramPacket(sendData,
                                        sendData.length, IpAddress, serverPort);
            
            // The packet is sent to the IP Address and port given in the above
            clientSocket.send(sendPacket);

            System.out.println("=============================================");
            packetCount--;
        }



        // This logic takes care of the remainding packet, if it exists.
        if(remainder == true)
        {
            byte[] lastPack;
            // Copying the remainding data into it's own byte array
            lastPack = Arrays.copyOf(sendData, lastPackLen);

            System.out.println("Last packet data:");
            System.out.println(new String(lastPack));

            // Making a new datagram, as always, but keeping the same Socket.
            DatagramPacket remainderPacket = new DatagramPacket(lastPack,
                                        lastPack.length, IpAddress, serverPort);
            clientSocket.send(remainderPacket);
        }

        clientSocket.close();
    }
}
