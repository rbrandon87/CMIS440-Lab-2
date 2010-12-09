package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket; //For sending data
import java.net.DatagramSocket; //For creating a connection to send data
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.net.PortUnreachableException;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane; //For Exception handling

/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** Creates a UDP connection on specified port/IP address;sends packet to server
 * Basically this class creates a Datagram socket and packet based on the given
 * port and IP address and sends any data waiting in the Buffer object to the
 * server.
*|----------------------------------------------------------------------------|
*|                           CRC: FileStatsProcessor                          |
*|----------------------------------------------------------------------------|
*|Send data in buffer to server                               Buffer          |
*|Update GUI Components                                       LabMainWindow   |
*|                                                                            |
*|----------------------------------------------------------------------------|
*
* @TheCs Cohesion - All methods in this class work together on similar task.
* Completeness - Completely creates word counter threads to process file input
* Convenience - There are sufficient methods and variables to complete the
*                required task.
* Clarity - The methods and variables are distinguishable and work in a
*           uniform manner to provide clarity to other programmers.
* Consistency - All names,parameters ,return values , and behaviors follow
*               the same basic rules.
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
    private final Random generator = new Random();
    private int numOfBytesSent = 0;
    private JLabel updateClientBytesSentLabel = null;
    private int sleepTime = 0;
    private final int MAXSLEEPTIME = 5;
  
    /**FileStatsProcessor Constructor;initialize variables and GUI reference
    * @TheCs Cohesion - FileStatsProcessor Constructor;initialize variables 
    *                   and GUI reference.
    * Completeness - Completely creates FileStatsProcessor Constructor;
    *               initialize variables and GUI reference.
    * Convenience - Simply creates FileStatsProcessor Constructor;
    *               initialize variables and GUI reference.
    * Clarity - It is simple to understand that this creates FileStatsProcessor
    *           Constructor; initialize variables and GUI reference.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param aServerIpAddress IP address for server
    * @param aServerPort port for server
    * @param aSharedBuffer Buffer object holding FileStats data ready to send
    * @param aNumOfFilesToProcess Number of files to read and process
    * @param alblClientBytesSentLabel Update GUI with # of bytes sent to server
    * @exception NumberFormatException when converting String to Integer
    * @exception Exception general exception capture
    */
    public FileStatsProcessor(String aServerIpAddress, int aServerPort, 
            Buffer aSharedBuffer, String aNumOfFilesToProcess ,
            JLabel alblClientBytesSentLabel){
        try {
            myServerIpAddress = aServerIpAddress;
            myServerPort = aServerPort;
            mySharedBuffer = aSharedBuffer;
            myDatagramSocket = new DatagramSocket();
            numOfFilesToProcess = "Total number of files : " +
                    aNumOfFilesToProcess + " :\n\n: ";
            /**
             * NumberFormatException for Integer parsing below
             */
            numOfFilesToProcessInteger = Integer.parseInt(aNumOfFilesToProcess);
            updateClientBytesSentLabel = alblClientBytesSentLabel;
            updateClientBytesSentLabel.setText("0");
            /** sleepTime is used in run method. It puts the thread to sleep for
             * a small amount of time in order to ensure all of the bytes are
             * sent. Without the sleep time, sometimes packets are missed.
             *
             */
            sleepTime = generator.nextInt(MAXSLEEPTIME);
            
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(null,"Number Format Exception "
                    + "on FileStatsProcessor Construct.\n"
                    + exception.getMessage(),"Number Format Exception",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,"Unknown Exception "
                    + "on FileStatsProcessor Construct.\n"
                    + exception.getMessage(),"Unknown Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**Creates a UDP connection and sends data held in Buffer object to Server.
    * @TheCs Cohesion - Creates a UDP connection and sends data held in Buffer
    *                   object to Server.
    * Completeness - Completely creates a UDP connection and sends data held in
    *                Buffer object to Server.
    * Convenience - Simply creates a UDP connection and sends data held in
    *               Buffer object to Server.
    * Clarity - It is simple to understand that this creates a UDP connection
    *           and sends data held in Buffer object to Server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception IllegalBlockingModeException if an associated channel is in
    *            non-blocking mode.
    * @exception PortUnreachableException maybe thrown if the socket connected
    *            to a currently unreachable destination.
    * @exception SecurityException in case of any security violations.
    * @exception InterruptedException in case of thread interruption.
    * @exception UnknownHostException indicate IP address of host could
    *            not be determined.
    * @exception SocketException for any error in the underlying protocol
    * @exception IOException I/O exception of some sort has occurred
    * @exception Exception general exception capture
    */
    public void run(){
        try{
            /**
             * Creates a DatagramPacket to be sent to the specified server. Then
             * it saves this information in numOfBytesSent which is used to
             * update a label on the GUI. Next, a for loop goes iterates based
             * on the number of files being processed, each loop pulls a
             * FileStats value from the Buffer object and sends it to the
             * server. The GUI is updated and then the thread sleeps for a
             * short period of time to ensure all packets are sent.
             */
            /**
             * This first byte array being sent contains the string that
             * tells how many files are being processed.
             */
            byte[] myNumOfFilesDataToSend =numOfFilesToProcess.toString()
                    .getBytes();
            DatagramPacket mySendNumOfFilesPacket = new DatagramPacket(
                    myNumOfFilesDataToSend, myNumOfFilesDataToSend.length,
                    InetAddress.getByName(myServerIpAddress), myServerPort);
            myDatagramSocket.send(mySendNumOfFilesPacket);
            numOfBytesSent = mySendNumOfFilesPacket.getLength();

            for(numOfFilesSent = 0; numOfFilesSent < numOfFilesToProcessInteger;
            numOfFilesSent++){
                /**
                 * tempLineHolder = "File x) + Shared Buffer data
                 */
                tempLineHolder = ":\n\n File " + 
                        Integer.toString(numOfFilesSent) + ") " +
                        mySharedBuffer.get().toString();
                byte[] myDataToSend = tempLineHolder.getBytes();
                DatagramPacket mySendPacket = new DatagramPacket(myDataToSend,
                        myDataToSend.length,
                        InetAddress.getByName(myServerIpAddress), myServerPort);
                myDatagramSocket.send(mySendPacket);
            numOfBytesSent += mySendPacket.getLength();
            /**
             * Update GUI with number of bytes sent and then sleeps for a short
             * period of time. NetBeans will put a warning out for the use of
             * Thread.sleep because of potential performance issues, but
             * I feel it is necessary to have to ensure data doesn't get lost.
             */
            updateClientBytesSentLabel.setText(String.valueOf(numOfBytesSent));
                Thread.sleep(sleepTime);
            }

            
        }catch (IllegalBlockingModeException exception){
            JOptionPane.showMessageDialog(null,"Illegal Blocking Mode Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Illegal Blocking Mode Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (PortUnreachableException exception){
            JOptionPane.showMessageDialog(null,"Port Unreachable Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Port Unreachable Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (SecurityException exception){
            JOptionPane.showMessageDialog(null,"Security Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Security Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (InterruptedException exception){
            /**
             * This should only occur when the user clicks the cancel button, so
             * I did not put a JOptionPane here; otherwise it would should
             * one for potentially many files.
             */
        }catch (UnknownHostException exception){
            JOptionPane.showMessageDialog(null,"Unknown Host Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Unknown Host Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (SocketException exception){
            JOptionPane.showMessageDialog(null,"Socket Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Socket Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (IOException exception){
            JOptionPane.showMessageDialog(null,"I/O Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"I/O Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception"
                    + "on FileStatsProcessor run method.\n"
                    + exception.getMessage(),"Unknown Exception",
                    JOptionPane.ERROR_MESSAGE);

        }


    }


}
