package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JOptionPane; //For Exception Handling


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
    private JTextArea updateOutputTextArea = null;

    public UdpServer(int aServerPort, JTextArea aJTextArea){
        try{
            myListenSocket = new DatagramSocket(aServerPort);
            updateOutputTextArea = aJTextArea;
            

        }catch (SocketException exception){

        }
    }

    public void run(){
        
        while (true){
            try {
                byte[] myIncomingData = new byte[1024];
                DatagramPacket myReceivePacket = new DatagramPacket(myIncomingData, myIncomingData.length);
                myListenSocket.receive(myReceivePacket);
                System.out.println(new String (myReceivePacket.getData()));
                updateOutputTextArea.append(new String (myReceivePacket.getData()));
            } catch (IOException exception) {
            }

        }

    }

}
