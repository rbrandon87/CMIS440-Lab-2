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
import java.util.Random;

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
    private JLabel updateTotalBytesReceivedLabel = null;
    private JLabel updateBytesReceivedLabel = null;
    private int bytesReceived = 0;
    private int totalBytesReceived = 0;
    private final Random generator = new Random();

    public UdpServer(int aServerPort, JTextArea aJTextArea, JLabel aDataCheckLabel, JLabel aBytesReceivedLabel, JLabel aTotalBytesReceivedLabel){
        try{
            myListenSocket = new DatagramSocket(aServerPort);
            updateOutputTextArea = aJTextArea;
            updateOutputLabel = aDataCheckLabel;
            updateOutputTextArea.setText("");
            updateOutputLabel.setText("*Waiting for Data...");

            updateBytesReceivedLabel = aBytesReceivedLabel;
            updateBytesReceivedLabel.setText("0");

            updateTotalBytesReceivedLabel = aTotalBytesReceivedLabel;
            updateTotalBytesReceivedLabel.setText("0");

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
                myListenSocket.setSoTimeout(Math.min(500,generator.nextInt(2000)));
                myListenSocket.receive(myReceivePacket);

                bytesReceived += myReceivePacket.getLength();
                totalBytesReceived += myReceivePacket.getLength();
                updateTotalBytesReceivedLabel.setText(String.valueOf(totalBytesReceived));
                updateOutputLabel.setText("*Receiving Data...");
                myReceivePacket.setLength(myReceivePacket.getLength());
                tempLineHolder = new String(myReceivePacket.getData()).trim();
                updateOutputTextArea.append(tempLineHolder);

            }catch (SocketTimeoutException exception) {
                if (bytesReceived != 0){
                    updateBytesReceivedLabel.setText(String.valueOf(bytesReceived));
                    bytesReceived = 0;
                }
                updateOutputLabel.setText("*Waiting for Data...");
            }catch (SocketException exception) {
                System.out.println(exception);
            } catch (IOException exception) {
                System.out.println(exception);
            }catch (NullPointerException exception) {
                System.out.println(exception); //If program runs twice on same port
            }catch (Exception exception) {
                System.out.println(exception);
            }

        }


    }

    public void stopIncomingConnections(){
        myListenSocket.close();
    }


}
