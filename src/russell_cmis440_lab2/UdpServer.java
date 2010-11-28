package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JLabel;
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
    private JLabel updateOutputLabel = null;

    public UdpServer(int aServerPort, JTextArea aJTextArea, JLabel aDataCheckLabel){
        try{
            myListenSocket = new DatagramSocket(aServerPort);
            updateOutputTextArea = aJTextArea;
            updateOutputLabel = aDataCheckLabel;
            updateOutputTextArea.setText("");
            updateOutputLabel.setText("*Waiting for Data...");
        }catch (SocketException exception){

        }
    }

    public void run(){
        String tempLineHolder = "";
        while(LabMainWindow.ServerRun) {
            try {
                byte[] myIncomingData = new byte[65507];
                tempLineHolder = "";
                DatagramPacket myReceivePacket = new DatagramPacket(myIncomingData, myIncomingData.length);
                myListenSocket.setSoTimeout(2000);
                myListenSocket.receive(myReceivePacket);
                updateOutputLabel.setText("*Receiving Data...");
                myReceivePacket.setLength(myReceivePacket.getLength());
                tempLineHolder = new String(myReceivePacket.getData()).trim();
                updateOutputTextArea.append(tempLineHolder);

            }catch (SocketTimeoutException exception) {
                updateOutputLabel.setText("*Waiting for Data...");
            }catch (SocketException exception) {
                System.out.println(exception);
            } catch (IOException exception) {
                System.out.println(exception);
            }catch (NullPointerException exception) {
                System.out.println(exception);
            }catch (Exception exception) {
                System.out.println(exception);
            }

        }


    }

    public void stopIncomingConnections(){
        myListenSocket.close();
    }


}
