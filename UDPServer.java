import java.io.*;
import java.net.*;

class UDPServer
{
    public static void main(String args[]) throws IOException
    {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] Data = new byte[1024];
        int count = 0;

        FileWriter file = new FileWriter("testResults.txt");
        PrintWriter out = new PrintWriter(file);

        while(true)
        {
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