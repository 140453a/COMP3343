// Name: Server.java
// Authors: Joshua Alexander, Joel Deighton
// Date: November 26, 2018
// Purpose: Basic UDP server with socket. When it receives a message, it then
//          sends a stream of packets over a period of time to the receiving
//          ip address. The form of the packet is (index)x0000... with the
//          resulting packet being 30 bytes long.

import java.net.*;
import java.util.Random;


public class Server {
    static final int MESSAGES = 100;
    static final int BYTE_SIZE = 30;
    static final int PORT_NUMBER = 9876;
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER);
        byte[] receiveData = new byte[BYTE_SIZE];
        byte[] sendData = new byte[BYTE_SIZE];
        byte[] finishedData = new byte[BYTE_SIZE];
        int count = 1;
        Random rand = new Random();

        while (true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);   

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();


            for (int i = 0; i < MESSAGES; i++)
            {
                String fill = Integer.toString(count) + "x";
                for (int j = (BYTE_SIZE / 2); j > String.valueOf(fill).length(); j--)
                {
                    fill = fill + "0";
                }
                sendData = fill.getBytes();
                DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);

                Thread.sleep(2);
                serverSocket.send(sendPacket);
                count = count + 1;
            }
            Thread.sleep(60000); // Wait one minute for all packets to come in
            String finishedMsg = "-1x";
            finishedData = finishedMsg.getBytes();
            for (int i = 0; i < 30; i++) 
            {
                DatagramPacket finishedPacket =
                new DatagramPacket(finishedData, finishedData.length, IPAddress, port);
                Thread.sleep(1000); // sleep 1 second, sending 30 messages over 30 seconds
                serverSocket.send(finishedPacket);
            }
            serverSocket.close();
            System.exit(0);
        }
    }
}
