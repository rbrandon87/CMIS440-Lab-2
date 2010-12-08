package russell_cmis440_lab2;

import java.io.IOException;
import java.net.DatagramPacket; //For receiving data
import java.net.DatagramSocket; //For creating a connection to receive data
import java.net.SocketException;
import java.net.SocketTimeoutException;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane; //For Exception Handling
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.*;

/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** Creates a UDP connection on specified port and listen for traffic
* Basically this class opens up a UDP connection and waits for incoming data.
* As data comes in it updates the referenced GUI components to reflect.
*|----------------------------------------------------------------------------|
*|                           CRC: UdpServer                                   |
*|----------------------------------------------------------------------------|
*|Listen on specified port                                    DfcServer       |
*|Update GUI Components                                       LabMainWindow   |
 |                                                                            |
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






public class UdpServer implements Runnable {

    private DatagramSocket myListenSocket;
    private JTextArea updateOutputTextArea = null; //txtServerOutput
    private JTextArea updateTotalOutputTextArea = null; //txtTotalOutput
    private JLabel updateOutputLabel = null; // lblDataCheck
    private JLabel updateTotalBytesReceivedLabel = null;//lblTotalServerBytesReceived
    private JLabel updateBytesReceivedLabel = null;//lblServerBytesReceived
    private int bytesReceived = 0;
    private int totalBytesReceived = 0;
    private final Random generator = new Random();
    private Map< Integer, Integer > myTotalWordMap =
            new HashMap< Integer, Integer >();
    private final int MINWAITTIME = 1000;//Socket Timeout
    private final int MAXWAITTIME = 2000;//Socket Timeout
    private final int PACKETSIZE = 4096;// Size of byte array for receiving

    /** Constructor for UdpServer; initializes variables and GUI references
    * @TheCs Cohesion - Constructor for UdpServer; initializes variables
    *                   and GUI references.
    * Completeness - Completely constructs for UdpServer; initializes
    *                variables and GUI references.
    * Convenience - Simply constructs for UdpServer; initializes
    *                variables and GUI references.
    * Clarity - It is simple to understand that this constructs for UdpServer;
    *           initializes variables and GUI references.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param aServerPort port number for server
    * @param aJOutputTextArea TextArea to display file information
    * @param aDataCheckLabel label shows data receiving or not
    * @param aBytesReceivedLabel label shows bytes received per send
    * @param aTotalBytesReceivedLabel label show total bytes received
    * @param aTxtTotalOutput TextArea show file information totals
    * @exception SocketException for DatagramSocket creation
    * @exception Exception general exception capture
    */
    public UdpServer(int aServerPort, JTextArea aJOutputTextArea, 
            JLabel aDataCheckLabel, JLabel aBytesReceivedLabel,
            JLabel aTotalBytesReceivedLabel, JTextArea aTxtTotalOutput){
        try{
            /**
             * myListenSocket Creates a UDP connection on the specified port
             * to listen for traffic.
             */
            myListenSocket = new DatagramSocket(aServerPort);
            
            updateOutputTextArea = aJOutputTextArea;
            updateOutputTextArea.setText("");
            updateTotalOutputTextArea = aTxtTotalOutput;
            updateTotalOutputTextArea.setText("");

            updateOutputLabel = aDataCheckLabel;
            updateOutputLabel.setText("*Waiting for Data...");

            updateBytesReceivedLabel = aBytesReceivedLabel;
            updateBytesReceivedLabel.setText("0");

            updateTotalBytesReceivedLabel = aTotalBytesReceivedLabel;
            updateTotalBytesReceivedLabel.setText("0");

        }catch (SocketException exception){
            JOptionPane.showMessageDialog(null,"Socket Execution Exception "
                    + "on UdpServer Construct.\n" + exception.getMessage(),
                    "Socket Execution Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception "
                    + "on UDpServer Construct.\n" + exception.getMessage(),
                    "Unknown Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Constructor for UdpServer; initializes variables and GUI references
    * @TheCs Cohesion - Constructor for UdpServer; initializes variables
    *                   and GUI references.
    * Completeness - Completely constructs for UdpServer; initializes
    *                variables and GUI references.
    * Convenience - Simply constructs for UdpServer; initializes
    *                variables and GUI references.
    * Clarity - It is simple to understand that this constructs for UdpServer;
    *           initializes variables and GUI references.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception SocketTimeoutException determines when not receiving data
    * @exception SocketException for DatagramSocket creation
    * @exception IOException for DatagramSocket traffic
    * @exception NullPointerException when two servers tries to run on same port
    * @exception NumberFormatException for use of Integer.parseInt method
    * @exception Exception general exception capture
    */
    public void run(){
        String tempLineHolder = "";//Hold incoming data
        /**
         * ServerRun is a static variable created in the LabMainWindow class.
         * It will determine when the loop should stop working. Mainly when
         * the 'Stop Listening' button is pushed.
         */
        while(LabMainWindow.ServerRun) {
            try {
                /**
                 * PACKETSIZE can be adjusted to find best size to prevent data
                 * loss, but not waste memory and to be compatible for Internet
                 * protocols.
                 */
                byte[] myIncomingData = new byte[PACKETSIZE];
                tempLineHolder = "";
                /** Creates a new packet named myReceivePacket that will hold
                 * incoming data. the .setSoTimeout is set to randomly generate
                 * between MINWAITTIME and MAXWAITTIME milliseconds.
                 * This basically tells the socket how long to wait after
                 * it has received data to continue waiting for new data.
                 */
                DatagramPacket myReceivePacket = new
                        DatagramPacket(myIncomingData, myIncomingData.length);
                myListenSocket.setSoTimeout(
                        Math.min(MINWAITTIME,generator.nextInt(MAXWAITTIME)));
                myListenSocket.receive(myReceivePacket);

                 // The next 9 lines just update the GUI with incoming data info
                bytesReceived += myReceivePacket.getLength();
                updateBytesReceivedLabel.setText(String.valueOf(bytesReceived));
                totalBytesReceived += myReceivePacket.getLength();
                updateTotalBytesReceivedLabel.
                        setText(String.valueOf(totalBytesReceived));
                updateOutputLabel.setText("*Receiving Data...");
                myReceivePacket.setLength(myReceivePacket.getLength());
                tempLineHolder = new String(myReceivePacket.getData()).trim();
                updateOutputTextArea.append(tempLineHolder);

                /**
                 * Regex will match the incoming file data to keep a running
                 * total of total word lengths along with their counts stored in
                 * a Map here on the Server side.
                 */
                /**
                 * This regex is setup to match the toString I setup, for
                 * example "|1               |               7|" would match
                 * the 1 and 7.
                 */
                String extRegex = "\\|(\\d*)\\s*\\|\\s*(\\d*)\\|";
                Pattern pattern = Pattern.compile(extRegex, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(tempLineHolder);
                while (matcher.find()){
                    if (matcher.group(1).length() != 0 &&
                            matcher.group(2).length() != 0){
                        /**
                         * If the regex matches and both groups hold data then
                         * add them to the Map.
                         */
                        addToTotalWordMap(Integer.parseInt(matcher.group(1)),
                                Integer.parseInt(matcher.group(2)));
                        updateTotalOutputTextArea.setText(this.toString());
                    }
                }
            }catch (SocketTimeoutException exception) {
                /**
                 * Once the Socket Timeout is done waiting for new data it will
                 * come here where I reset the byte counter for the Bytes
                 * Received label per send. Also update the data check label
                 * which indicates when the server has stopped actively
                 * receiving data.
                 */
                if (bytesReceived != 0){
                    bytesReceived = 0;
                }
                updateOutputLabel.setText("*Waiting for Data...");
            }catch (SocketException exception) {
                /**
                 * If exception is "socket closed" then only show information
                 * message indicating the server was brought down; otherwise
                 * if equal to anything else throw an error message since 
                 * something is wrong.
                 */
                if (exception.getMessage().equals("socket closed")){
                    JOptionPane.showMessageDialog(null, "Server Socket Closed.",
                            "Server Shutdown",JOptionPane.INFORMATION_MESSAGE);

                }else{
                    JOptionPane.showMessageDialog(null,"Socket Exception "
                            + "on UdpServer run.\n" + exception.getMessage(),
                            "Socket Exception",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(null,"IO Exception "
                        + "on UdpServer run.\n" + exception.getMessage(),
                        "Socket Exception",
                        JOptionPane.ERROR_MESSAGE);
            }catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null,"Number Format Exception "
                        + "on UdpServer run.\n"
                        + exception.getMessage(),
                        "Number Format Exception",
                        JOptionPane.ERROR_MESSAGE);
            }catch (NullPointerException exception) {
                JOptionPane.showMessageDialog(null,"Null Pointer Exception "
                        + "on UdpServer run.\n"
                        + exception.getMessage(),
                        "Null Pointer Exception",
                        JOptionPane.ERROR_MESSAGE);
            }catch (Exception exception) {
                JOptionPane.showMessageDialog(null,"Unknown Exception "
                        + "on UdpServer run.\n" + exception.getMessage(),
                        "Unknown Exception",
                        JOptionPane.ERROR_MESSAGE);
            }

        }


    }

    /** Closes myListenSocket to stop accepting any more incoming data
    * @TheCs Cohesion - Closes myListenSocket to stop accepting any
    *                   more incoming data.
    * Completeness - Completely closes myListenSocket to stop accepting
    *                any more incoming data.
    * Convenience - Simply closes myListenSocket to stop accepting
    *                any more incoming data.
    * Clarity - It is simple to understand that this closes myListenSocket to
    *           stop accepting any more incoming data.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception Exception general exception capture
    */
    public void stopIncomingConnections(){
        try{
            myListenSocket.close();
        }catch(Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception "
                    + "on UdpServer closing.\n" + exception.getMessage(),
                    "Uknown Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Updates running total of file data received from UDP port
    * @TheCs Cohesion - Updates running total of file data received from UDP
    *                   port.
    * Completeness - Completely updates running total of file data received
    *                from UDP port.
    * Convenience - Simply updates running total of file data received
    *                from UDP port.
    * Clarity - It is simple to understand that this updates running total of
    *           file data received from UDP port.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param aWord contains the word length to be added to the map.
    * @param aWordCount contains current count of word being added.
    */
    public void addToTotalWordMap(Integer aWordLen, Integer aWordCount){
        if (myTotalWordMap.containsKey(aWordLen)){
            int count = myTotalWordMap.get(aWordLen);
            myTotalWordMap.put(aWordLen, count+aWordCount); //increase count
        }
        else{
            myTotalWordMap.put(aWordLen, aWordCount); //New word; put in map
        }
    }

    /** Returns the current word map with total unique words lengths and count
    * @TheCs Cohesion - Returns the current word map with total unique
    *                   words lengths and count.
    * Completeness - Completely returns the current word map with total unique
    *                words lengths and count.
    * Convenience - Simply returns the current word map with total unique
    *               words lengths and count.
    * Clarity - It is simple to understand that this returns returns the
    *           current word map with total unique words lengths and count.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public Map< Integer, Integer > getWordMap(){
        return myTotalWordMap;
    }


    /** Overrides the toString method to provide more meaningful content. I
    * decided to print a table format of word length and then count as well as
    * totals at the bottom to give a good picture of what the incoming file
    * data contains.
    * @TheCs Cohesion - Overrides the toString method to provide more meaningful
    *                   content.
    * Completeness - Completely overrides the toString method to provide more
    *                meaningful content.
    * Convenience - Simply overrides the toString method to provide more
    *               meaningful content.
    * Clarity - It is simple to understand that this overrides the toString
    *           method to provide more meaningful content.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    @Override
    public String toString(){
        /**
         * Basically I use formattedLine with format to form the strings in a
         * particular way to aid in readability of the output and I then append
         * this to a StringBuilder, myFormattedResults, and eventually return
         * the string format of this StringBuilder.
         */
        StringBuilder myFormattedResults = new StringBuilder();
        String formattedLine = "";
        int maxLength = 15; //Padding between word length and count/readability
        int numberOfWords = 0;
        /**
         * format defines a standard format for the output to assist with
         * readability.
         */
        String format = "|%1$-"+ maxLength +"s | %2$"+ maxLength +"s|\n";

        myFormattedResults.append("-----------------------------------\n");
        formattedLine = String.format(format,"Word Length","Total Count");
        myFormattedResults.append(formattedLine);
        formattedLine = String.format(format,"---------------",
                "---------------");
        myFormattedResults.append(formattedLine);

        Set< Integer > keys = getWordMap().keySet();//Get the keys
        TreeSet < Integer > sortedKeys = new TreeSet< Integer >( keys);//sort keys

        for (Integer key : sortedKeys){
            /**
             * If key is less than 15 characters print the entire key, otherwise
             * print a substring of the key contains the first 15 characters.
             */
            formattedLine = String.format(format,key.toString().length() <=
                    maxLength ? key : key.toString().substring(0, maxLength),
                                                        getWordMap().get(key));
            myFormattedResults.append(formattedLine);
            numberOfWords += getWordMap().get(key);
        }

        myFormattedResults.append("-----------------------------------\n");
        formattedLine = "Total number of unique word lengths found : " +
                keys.size() + " :\n" + "Total number of word lengths found : " +
                numberOfWords + " :\n\n";
        myFormattedResults.append(formattedLine);

        return myFormattedResults.toString();
    }


}
