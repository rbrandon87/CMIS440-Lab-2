package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
* Program Name:
* @author Brandon R Russell
* @Course CMIS440
* Date:
* Program Requirements:
* Program Design:
* Things you what me to know before I grade your work:
*/

/** What is this?*
*|----------------------------------------------------------------------------|
*|                                CRC:                                        |
*|----------------------------------------------------------------------------|
*|What it is*                            What class uses this*                |
*|----------------------------------------------------------------------------|
*/





public class FileStatsProcessor implements Runnable{
    
    private String myServerIpAddress = "";
    private int myServerPort = 0;
    private Buffer mySharedBuffer = null;
    private DatagramSocket myDatagramSocket = null;

    public FileStatsProcessor(String aServerIpAddress, int aServerPort, Buffer aSharedBuffer ){
        try {
            myServerIpAddress = aServerIpAddress;
            myServerPort = aServerPort;
            mySharedBuffer = aSharedBuffer;
            myDatagramSocket = new DatagramSocket();
        } catch (SocketException exception) {
        }
    }

    public void run(){
        try{
            byte[] myDataToSend = mySharedBuffer.get().toString().getBytes();
            DatagramPacket mySendPacket = new DatagramPacket(myDataToSend, myDataToSend.length, InetAddress.getByName(myServerIpAddress), myServerPort);
            myDatagramSocket.send(mySendPacket);

        }catch (UnknownHostException exception){

        }catch (IOException exception){
            
        }


    }

}
