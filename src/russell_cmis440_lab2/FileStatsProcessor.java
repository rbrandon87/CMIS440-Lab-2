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
    private String numOfFilesToProcess = "";
    private int numOfFilesToProcessInteger = 0;
    private int numOfFilesSent = 0;
    private String tempLineHolder = "";
    public FileStatsProcessor(String aServerIpAddress, int aServerPort, Buffer aSharedBuffer, String aNumOfFilesToProcess ){
        try {
            myServerIpAddress = aServerIpAddress;
            myServerPort = aServerPort;
            mySharedBuffer = aSharedBuffer;
            myDatagramSocket = new DatagramSocket();
            numOfFilesToProcess = "Total number of files:" + aNumOfFilesToProcess + "\n\n";
            numOfFilesToProcessInteger = Integer.parseInt(aNumOfFilesToProcess);
        } catch (NumberFormatException exception) {
        } catch (SocketException exception) {
        }
    }

    public void run(){
        try{
            byte[] myNumOfFilesDataToSend =numOfFilesToProcess.getBytes();
            DatagramPacket mySendNumOfFilesPacket = new DatagramPacket(myNumOfFilesDataToSend, numOfFilesToProcess.length(), InetAddress.getByName(myServerIpAddress), myServerPort);
            myDatagramSocket.send(mySendNumOfFilesPacket);

            for(numOfFilesSent = 0; numOfFilesSent < numOfFilesToProcessInteger; numOfFilesSent++){
                tempLineHolder = "\n\nFile " + Integer.toString(numOfFilesSent) + ")" + mySharedBuffer.get().toString() + "\n\n";
                byte[] myDataToSend = tempLineHolder.getBytes();
                DatagramPacket mySendPacket = new DatagramPacket(myDataToSend, myDataToSend.length, InetAddress.getByName(myServerIpAddress), myServerPort);
                myDatagramSocket.send(mySendPacket);
                Thread.sleep(5);
                //System.out.println("File: " + numOfFilesSent + " sent.");
                //numOfFilesSent++;
            }

        }catch (InterruptedException exception){

        }catch (UnknownHostException exception){

        }catch (IOException exception){
            
        }


    }

}
