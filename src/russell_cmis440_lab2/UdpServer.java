package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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





public class UdpServer implements Runnable {

    private DatagramSocket myListenSocket;

    public UdpServer(int aServerPort){
        try{
            myListenSocket = new DatagramSocket(aServerPort);

        }catch (SocketException exception){

        }
    }

    public void run(){

        while (true){
            try {
                byte[] myIncomingData = new byte[100];
                DatagramPacket myReceivePacket = new DatagramPacket(myIncomingData, myIncomingData.length);
                myListenSocket.receive(myReceivePacket);
            } catch (IOException exception) {
            }

        }

    }

}
