import java.io.*;
import java.net.*;
import java.util.Arrays;

class UDPClient
{
    static final int serverPort = 9876;
    static final int MAX_SIZE = 1024;
    public static void main(String args[]) throws SocketException, IOException
    {
        int count = 0;


        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IpAddress = InetAddress.getByName("165.227.159.60");

        byte[] sendData = new byte[MAX_SIZE];

        String filePath = "testSend.txt";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);


        int fileLength = 0; 

        while((count = fis.read(sendData)) != -1)    //calculate total length of file
        {
            fileLength += count;
        }

        System.out.println("Total Length :" + fileLength);

        int packetCount = fileLength/MAX_SIZE;
        System.out.println("Number of packets : " + packetCount);

        int offset = packetCount * MAX_SIZE;  //calculate offset. it total length of file is 1024 and array size is 1000 den starting position of last packet is 1001. this value is stored in offset.

        int lastPackLen = fileLength - offset;
        System.out.println("\nLast packet Length : " + lastPackLen);

        byte[] lastPack = new byte[lastPackLen-1];  //create new array without redundant information


        fis.close();

        FileInputStream fis1 = new FileInputStream(file);
        //while((count = fis1.read(sendData)) != -1 && (packetCount!=0))
        while((count = fis1.read(sendData)) != -1 )
        { 
            if(packetCount <= 0)
            {
                break;
            }

            System.out.println(new String(sendData));
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, serverPort);
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