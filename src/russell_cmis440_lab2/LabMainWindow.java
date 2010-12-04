package russell_cmis440_lab2;

/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19,2010
* IDE: NetBeans 6.9.1
* OS: Windows 7
* Java: JDK 1.6.0_22
* Files: LabMainWindow.java, DfcServer.java, UdpServer.java,
*        FileStatsProcessor.java, Buffer.java, FileProcessor.java,
*        FileStats.java, DfcClient.java
*
* Program Requirements:
*Client:
*Must explicitly handle all file related,network related, thread related
*exceptions as a minimum.
*File statistics class named FileStats that has getter / setter methods for file
*name and a Map with a key of type Integer that represents the length of the
*word and the value of type Integer that represents the count of words of that
*length; and an overridden toString method that formats the filename and Map.
*A shared buffer class named Buffer that uses the ArrayBlockingQueue class
*implemented with the FileStats class.
*A file processing class named FileProcessor (this is the Producer) that takes
*the file name to be processed and the shared buffer instance in the
*constructor and whose 'run' method will process the text file by extracting
*all the words, where a word is defined as any sequence of alphabetic characters
*plus the apostophe, i.e., given: the quick brown fox's collar has 4 buttons;
*there are 7 words: the, quick, brown, fox's, collar, has, buttons. Using the
*extracted words you will keep count of the number of words at each length
*, i.e., using the above example, you would update the Map entry for 3 after
*processing the word 'the', update the Map entry 5 after processing the words
*'quick' and 'brown', etc. After processing the file, you will add the final
*FileStats instance to the shared buffer.
*A FileStats processing class named FileStatsProcessor (this is the Consumer)
*that takes the IP address or host name of the Server application,
*the port number and the shared buffer instance in the constructor. The 'run'
*method will create a Datagram socket for sending the FileStats then extract
*FileStats instances from the shared buffer as they become available and format
*the FileStats instance using its toString method and send the resulting bytes
*to the server.
*A distributed file counter client class named DfcClient whose 'main' method
*will create the shared buffer, create an instance of the ExecutorService and
*create the FileStatsProcessor and add to this ExecutorService instance; get a
*listing of all the files in a chosen directory, create a separate instance of
*the Executor and then for each file in the chosen directory create an instance
*of FileProcessor and add to the ExecutorService instance, wait for all the
*FileProcessor instances to finish and then terminate the client application.
*Server:
*Must explicitly handle all network related, thread related exceptions as a
*minimum.
*A UDP port monitor class named UdpServer that takes a port in the constructor
*and whose 'run' method opens the UDP socket on the given port and prints all
*received datagrams to the console as strings.
*A distributed file counter server class named DfcServer whose 'main' method
*will create an instance of UdpServer and place it in its own Thread and then
*wait indefinately (we are not going to worry about gracefully shutting
*down the server).
*Assignment Optional Implementations:
*Create a GUI for the Client and / or Server application.
*Enhance the individual file statistics gathered and sent to the server.
*Enhance the server side processing of the statistics to hold running totals
*, averages, etc.
*Add a simple menu to the server that accepts a 'quit' command that gracefully
*will stop the UdpServer instance and terminate the application.
*
*Program Design: I designed this program so one can, type in a IP address/Port
*on the client side, choose one or more files, or an entire directory, click
*the start button and have the server side receive the data. On the Server side
*one only needs to specify the port to listen on, then click the 'Start
*Listening' button and then wait to receive input. For testing purposes, one
*can designate the IP address of the server, on the client side, as localhost.
*
*
* Things you what me to know before I grade your work: I used the NetBeans
* Graphic designer to create the GUI portion of this program.
*/

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;
import java.awt.Cursor;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.InetAddress;
import java.util.concurrent.CancellationException;
import java.util.regex.*;
/** This is the main window of operation for the entire program. The purpose of
* this class is to create a GUI Interface that will allow the user to make
* selections of text files for the purpose of finding the unique words used and
* the count of these words along with their lengths.
*|----------------------------------------------------------------------------|
*|                                CRC: LabMainWindow                          |
*|----------------------------------------------------------------------------|
*|Creates GUI Interface                                                       |
*|Initialize DfcClient object/ Start File Processing           DfcClient      |
*|Initialize DfcServer object/ Start wait for data             DfcServer      |
*|----------------------------------------------------------------------------|
*
* @TheCs Cohesion - All methods in this class work together on similar task.
* Completeness - Completely creates/runs a DfcClient and/or DfcServer object(s)
* and provides output of file data.
* Convenience - There are sufficient methods and variables to complete the
*                required task.
* Clarity - The methods and variables are distinguishable and work in a
*           uniform manner to provide clarity to other programmers.
* Consistency - All names,parameters ,return values , and behaviors follow
*               the same basic rules.
*/



        
public class LabMainWindow extends javax.swing.JFrame 
        implements PropertyChangeListener{

    /** Creates new form LabMainWindow */
    public LabMainWindow() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clientPanel = new javax.swing.JPanel();
        lblConnectTo = new javax.swing.JLabel();
        txtClientIpAddress = new javax.swing.JTextField();
        lblClientIpAddress = new javax.swing.JLabel();
        txtClientPort = new javax.swing.JTextField();
        lblClientPort = new javax.swing.JLabel();
        myFileChooser = new javax.swing.JFileChooser();
        lblChooseFiles = new javax.swing.JLabel();
        btnClientStart = new javax.swing.JButton();
        btnClientCancel = new javax.swing.JButton();
        clientProgressBar = new javax.swing.JProgressBar();
        lbClientBytesLabel = new javax.swing.JLabel();
        lblClientBytesSent = new javax.swing.JLabel();
        serverPanel = new javax.swing.JPanel();
        lblServerPort = new javax.swing.JLabel();
        txtServerPort = new javax.swing.JTextField();
        lblServerIpAddress = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtServerOutput = new javax.swing.JTextArea();
        lblServerOutput = new javax.swing.JLabel();
        btnServerStart = new javax.swing.JButton();
        btnServerStop = new javax.swing.JButton();
        serverProgressBar = new javax.swing.JProgressBar();
        btnClearServerOutput = new javax.swing.JButton();
        lblDataCheck = new javax.swing.JLabel();
        lblServerActualIp = new javax.swing.JLabel();
        lblTotalServerBytesLabel = new javax.swing.JLabel();
        lblTotalServerBytesReceived = new javax.swing.JLabel();
        lblServerBytesReceived = new javax.swing.JLabel();
        lblServerBytesLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtTotalOutput = new javax.swing.JTextArea();
        lblTotalOutputTextArea = new javax.swing.JLabel();
        myMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        startClientMenuItem = new javax.swing.JMenuItem();
        startServerMenuItem = new javax.swing.JMenuItem();
        stopClientMenuItem = new javax.swing.JMenuItem();
        stopServerMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        copyOutputMenuItem = new javax.swing.JMenuItem();
        copyOutputTotalMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        clientInstructionsMenuItem = new javax.swing.JMenuItem();
        serverInstructionsMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CMIS440 Lab 2 Client/Server Word Length Counter");
        setBackground(javax.swing.UIManager.getDefaults().getColor("Nb.Desktop.background"));

        clientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Client:"));

        lblConnectTo.setText("Connect to:");

        txtClientIpAddress.setText("127.0.0.1");

        lblClientIpAddress.setText("IP Address");

        txtClientPort.setText("5000");

        lblClientPort.setText("Port");

        myFileChooser.setControlButtonsAreShown(false);
        myFileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        myFileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
        myFileChooser.setMultiSelectionEnabled(true);

        lblChooseFiles.setText("Choose the files to process:");

        btnClientStart.setText("Start");
        btnClientStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientStartActionPerformed(evt);
            }
        });

        btnClientCancel.setText("Cancel");
        btnClientCancel.setEnabled(false);
        btnClientCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientCancelActionPerformed(evt);
            }
        });

        clientProgressBar.setStringPainted(true);

        lbClientBytesLabel.setText("Bytes Sent:");

        lblClientBytesSent.setText("0");

        javax.swing.GroupLayout clientPanelLayout = new javax.swing.GroupLayout(clientPanel);
        clientPanel.setLayout(clientPanelLayout);
        clientPanelLayout.setHorizontalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(clientPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblConnectTo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClientIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clientPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblClientIpAddress)
                                .addGap(39, 39, 39)))
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(clientPanelLayout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(lblClientPort))
                            .addGroup(clientPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtClientPort, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblChooseFiles))
                    .addComponent(myFileChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnClientStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClientCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clientProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbClientBytesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblClientBytesSent, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        clientPanelLayout.setVerticalGroup(
            clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblConnectTo)
                        .addComponent(txtClientIpAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(clientPanelLayout.createSequentialGroup()
                        .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblClientPort)
                            .addComponent(lblClientIpAddress))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClientPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblChooseFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myFileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnClientStart)
                        .addComponent(btnClientCancel))
                    .addComponent(clientProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(clientPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbClientBytesLabel)
                    .addComponent(lblClientBytesSent)))
        );

        serverPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Server:"));

        lblServerPort.setText("Listen on Port:");

        txtServerPort.setText("5000");

        lblServerIpAddress.setText("External IP Address:");

        txtServerOutput.setColumns(20);
        txtServerOutput.setEditable(false);
        txtServerOutput.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtServerOutput.setRows(5);
        jScrollPane1.setViewportView(txtServerOutput);

        lblServerOutput.setText("Output:");

        btnServerStart.setText("Start Listening");
        btnServerStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServerStartActionPerformed(evt);
            }
        });

        btnServerStop.setText("Stop Listening");
        btnServerStop.setEnabled(false);
        btnServerStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServerStopActionPerformed(evt);
            }
        });

        btnClearServerOutput.setText("Clear");
        btnClearServerOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearServerOutputActionPerformed(evt);
            }
        });

        lblDataCheck.setText("*Waiting to Start...");

        lblServerActualIp.setText("127.0.0.1");

        lblTotalServerBytesLabel.setText("Total Bytes Received:");

        lblTotalServerBytesReceived.setText("0");

        lblServerBytesReceived.setText("0");

        lblServerBytesLabel.setText("Bytes Received:");

        txtTotalOutput.setColumns(20);
        txtTotalOutput.setEditable(false);
        txtTotalOutput.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        txtTotalOutput.setRows(5);
        jScrollPane2.setViewportView(txtTotalOutput);

        lblTotalOutputTextArea.setText("Output Totals:");

        javax.swing.GroupLayout serverPanelLayout = new javax.swing.GroupLayout(serverPanel);
        serverPanel.setLayout(serverPanelLayout);
        serverPanelLayout.setHorizontalGroup(
            serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serverPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(lblServerPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblServerIpAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblServerActualIp, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(186, Short.MAX_VALUE))
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(lblServerOutput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearServerOutput)
                        .addContainerGap(452, Short.MAX_VALUE))
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnServerStop)
                            .addGroup(serverPanelLayout.createSequentialGroup()
                                .addComponent(btnServerStart)
                                .addGap(10, 10, 10)
                                .addComponent(serverProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDataCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(165, Short.MAX_VALUE))
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(lblServerBytesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblServerBytesReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(lblTotalServerBytesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalServerBytesReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addComponent(lblTotalOutputTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        serverPanelLayout.setVerticalGroup(
            serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(serverPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServerPort)
                    .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblServerIpAddress)
                    .addComponent(lblServerActualIp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServerOutput)
                    .addComponent(btnClearServerOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(lblTotalOutputTextArea)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(serverPanelLayout.createSequentialGroup()
                        .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(serverProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDataCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnServerStop))
                    .addComponent(btnServerStart))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(serverPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalServerBytesLabel)
                    .addComponent(lblTotalServerBytesReceived)
                    .addComponent(lblServerBytesLabel)
                    .addComponent(lblServerBytesReceived)))
        );

        fileMenu.setText("File");

        startClientMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        startClientMenuItem.setText("Start Client");
        startClientMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startClientMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(startClientMenuItem);

        startServerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        startServerMenuItem.setText("Start Server");
        startServerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(startServerMenuItem);

        stopClientMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        stopClientMenuItem.setText("Stop Client");
        stopClientMenuItem.setEnabled(false);
        stopClientMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopClientMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(stopClientMenuItem);

        stopServerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        stopServerMenuItem.setText("Stop Server");
        stopServerMenuItem.setEnabled(false);
        stopServerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopServerMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(stopServerMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        myMenuBar.add(fileMenu);

        editMenu.setText("Edit");

        copyOutputMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyOutputMenuItem.setText("Copy Output");
        copyOutputMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyOutputMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyOutputMenuItem);

        copyOutputTotalMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        copyOutputTotalMenuItem.setText("Copy Total Output");
        copyOutputTotalMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyOutputTotalMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyOutputTotalMenuItem);

        myMenuBar.add(editMenu);

        helpMenu.setText("Help");

        clientInstructionsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        clientInstructionsMenuItem.setText("Client Instructions");
        clientInstructionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientInstructionsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(clientInstructionsMenuItem);

        serverInstructionsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        serverInstructionsMenuItem.setText("Server Instructions");
        serverInstructionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverInstructionsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(serverInstructionsMenuItem);

        myMenuBar.add(helpMenu);

        setJMenuBar(myMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clientPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serverPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(serverPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clientPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Calls the startProcessingFiles method to begin the file processing.
    * @TheCs Cohesion - Call startProcessingFiles method; begin file processing.
    * Completeness - Completely calls the startProcessingFiles method and begins
    *                the file processing.
    * Convenience - Simply calls the startProcessingFiles method and begins the
    *               file processing.
    * Clarity - It is simple to understand that this calls the
     *          startProcessingFiles method to begin the file processing.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void btnClientStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientStartActionPerformed
        startProcessingFiles();
    }//GEN-LAST:event_btnClientStartActionPerformed

    /** Calls the startServerListening method;begin wait for data on the server
    * @TheCs Cohesion - Call startServerListening method; begin wait for data.
    * Completeness - Completely calls the startServerListening method and begins
    *                waiting to receive data on the server.
    * Convenience - Simply calls the startServerListening method and begins
    *               waiting to receive data on the server.
    * Clarity - It is simple to understand that this calls the
    *           startServerListening method and begins waiting to receive
    *           data on the server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void btnServerStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServerStartActionPerformed
        startServerListening();
    }//GEN-LAST:event_btnServerStartActionPerformed

    /** Allows user to copy the selected text from the txtOutput TextArea.
    * @TheCs Cohesion - Allow user to copy the text from the txtOutput TextArea.
    * Completeness - Completely lets one copy selected text from the txtOutput
    *                TextArea.
    * Convenience - Simply allows one to copy selected text from the txtOutput
    *               TextArea
    * Clarity - It is simple to understand that this allows one to copy selected
    *           text from the txtOutput TextArea.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void copyOutputMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyOutputMenuItemActionPerformed
        try{
             /**This places selected text from the txtServerOutput TextArea
             * onto a newly created Clipboard object
             */
            Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection txtOutputSel =
                    new StringSelection(txtServerOutput.getSelectedText());
            system.setContents(txtOutputSel, txtOutputSel);

        }catch(Exception exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "Exception Thrown on copy action",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_copyOutputMenuItemActionPerformed

    /** Calls the stopServerListening method;stop wait for data on the server
    * @TheCs Cohesion - Call stopServerListening method; stop wait for data.
    * Completeness - Completely calls the stopServerListening method and stops
    *                waiting to receive data on the server.
    * Convenience - Simply calls the stopServerListening method and stops
    *               waiting to receive data on the server.
    * Clarity - It is simple to understand that this calls the
    *           stopServerListening method and stops waiting to receive
    *           data on the server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void btnServerStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServerStopActionPerformed
        stopServerListening();
    }//GEN-LAST:event_btnServerStopActionPerformed

    /** Clears the txtServerOutput/txtTotalOutput TextAreas of all data.
    * @TheCs Cohesion - Clears the txtServerOutput/txtTotalOutput TextAreas
    *                   of all data.
    * Completeness - Completely clears the txtServerOutput/txtTotalOutput
    *                TextAreas of all data.
    * Convenience - Simply clears the txtServerOutput/txtTotalOutput TextAreas
    *               of all data.
    * Clarity - It is simple to understand that this clears the 
    *           txtServerOutput/txtTotalOutput TextAreas of all data.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void btnClearServerOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearServerOutputActionPerformed
        txtServerOutput.setText("");
        txtTotalOutput.setText("");
    }//GEN-LAST:event_btnClearServerOutputActionPerformed

    /** Calls the startServerListening method;begin wait for data on the server
    * @TheCs Cohesion - Call startServerListening method; begin wait for data.
    * Completeness - Completely calls the startServerListening method and begins
    *                waiting to receive data on the server.
    * Convenience - Simply calls the startServerListening method and begins
    *               waiting to receive data on the server.
    * Clarity - It is simple to understand that this calls the
    *           startServerListening method and begins waiting to receive
    *           data on the server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void startServerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startServerMenuItemActionPerformed
        startServerListening();
    }//GEN-LAST:event_startServerMenuItemActionPerformed

    /** Calls the startProcessingFiles method to begin the file processing.
    * @TheCs Cohesion - Call startProcessingFiles method; begin file processing.
    * Completeness - Completely calls the startProcessingFiles method and begins
    *                the file processing.
    * Convenience - Simply calls the startProcessingFiles method and begins the
    *               file processing.
    * Clarity - It is simple to understand that this calls the
     *          startProcessingFiles method to begin the file processing.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void startClientMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startClientMenuItemActionPerformed
        startProcessingFiles();
    }//GEN-LAST:event_startClientMenuItemActionPerformed

    /** Properly exits the program.
    * @TheCs Cohesion - Properly exits the program.
    * Completeness - Completely exits the program.
    * Convenience - Simply exits the program.
    * Clarity - It is simple to understand that this exits the program.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /** Opens a JOptionPane Dialog to give simple instructions on client usage
    * @TheCs Cohesion - Open JOptionPane Dialog; instructions on client usage.
    * Completeness - Completely opens a JOptionPane Dialog to give simple
    *                instructions on client usage.
    * Convenience - Simply opens a JOptionPane Dialog; gives simple instructions
    *               on client usage.
    * Clarity - It is simple to understand that this opens a JOptionPane Dialog
    *           to give simple instructions on usage of the client.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void clientInstructionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clientInstructionsMenuItemActionPerformed
        String instructionMessage = ""
                + "1) Type in the IP Address/Port of Server.\n"
                + "2) Select one of more text files, or a directory containing"
                + " the text files.\n"
                + "3) Click the 'Start' button to send data to Server.";

        JOptionPane.showMessageDialog(null, instructionMessage,
            "Client Instructions", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_clientInstructionsMenuItemActionPerformed

    /** Calls the stopProcessingFiles method to stop the file processing.
    * @TheCs Cohesion - Call stopProcessingFiles method; stop file processing.
    * Completeness - Completely calls the stopProcessingFiles method and stops
    *                the file processing.
    * Convenience - Simply calls the stopProcessingFiles method and stops the
    *               file processing.
    * Clarity - It is simple to understand that this calls the
     *          stopProcessingFiles method to stop the file processing.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void stopClientMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopClientMenuItemActionPerformed
        stopProcessingFiles();
    }//GEN-LAST:event_stopClientMenuItemActionPerformed

    /** Calls the stopProcessingFiles method to stop the file processing.
    * @TheCs Cohesion - Call stopProcessingFiles method; stop file processing.
    * Completeness - Completely calls the stopProcessingFiles method and stops
    *                the file processing.
    * Convenience - Simply calls the stopProcessingFiles method and stops the
    *               file processing.
    * Clarity - It is simple to understand that this calls the
     *          stopProcessingFiles method to stop the file processing.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void btnClientCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientCancelActionPerformed
        stopProcessingFiles();
    }//GEN-LAST:event_btnClientCancelActionPerformed

    /** Calls the stopServerListening method;stop wait for data on the server
    * @TheCs Cohesion - Call stopServerListening method; stop wait for data.
    * Completeness - Completely calls the stopServerListening method and stops
    *                waiting to receive data on the server.
    * Convenience - Simply calls the stopServerListening method and stops
    *               waiting to receive data on the server.
    * Clarity - It is simple to understand that this calls the
    *           stopServerListening method and stops waiting to receive
    *           data on the server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void stopServerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopServerMenuItemActionPerformed
        stopServerListening();
    }//GEN-LAST:event_stopServerMenuItemActionPerformed

    /** Opens a JOptionPane Dialog to give simple instructions on server usage
    * @TheCs Cohesion - Open JOptionPane Dialog; instructions on server usage.
    * Completeness - Completely opens a JOptionPane Dialog to give simple
    *                instructions on server usage.
    * Convenience - Simply opens a JOptionPane Dialog; gives simple instructions
    *               on server usage.
    * Clarity - It is simple to understand that this opens a JOptionPane Dialog
    *           to give simple instructions on usage of the server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void serverInstructionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverInstructionsMenuItemActionPerformed
        String instructionMessage = ""
                + "1) Enter desired port for Server to listen on.\n"
                + "2) Click the 'Start Listening' button and wait for data to "
                + "arrive from the client.";

        JOptionPane.showMessageDialog(null, instructionMessage,
            "Server Instructions", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_serverInstructionsMenuItemActionPerformed

    /** Allows user to copy the selected text from the txtOutput TextArea.
    * @TheCs Cohesion - Allow user to copy the text from the txtOutput TextArea.
    * Completeness - Completely lets one copy selected text from the txtOutput
    *                TextArea.
    * Convenience - Simply allows one to copy selected text from the txtOutput
    *               TextArea
    * Clarity - It is simple to understand that this allows one to copy selected
    *           text from the txtOutput TextArea.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private void copyOutputTotalMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyOutputTotalMenuItemActionPerformed
        try{
             /**This places selected text from the txtTotalOutput TextArea
             * onto a newly created Clipboard object
             */
            Clipboard system = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection txtOutputSel =
                    new StringSelection(txtTotalOutput.getSelectedText());
            system.setContents(txtOutputSel, txtOutputSel);

        }catch(Exception exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "Exception Thrown on copy action",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_copyOutputTotalMenuItemActionPerformed

    /** Main method that starts the program by making the GUI visible.
    * @TheCs Cohesion - Starts the program by making the GUI visible.
    * Completeness - Completely makes the GUI visible.
    * Convenience - Simply makes the GUI visible.
    * Clarity - It is simple to understand that this makes the GUI visible.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    *
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LabMainWindow().setVisible(true);
            }
        });
    }

    /** Creates DfcClient object to begin file processing.
    * Creates a DfcClient object and executes it on a new
    * SwingWorker worker thread so as not to freeze the GUI during processing.
    * @TheCs Cohesion - Creates DfcClient object to begin file processing.
    * Completeness - Completely creates DfcClient object to begin
    *                file processing.
    * Convenience - Simply creates DfcClient object to begin file processing
    * Clarity - It is simple to understand that this creates a DfcClient
    *           object to begin file processing.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @precondition Must have at least one text file selected for processing.
    * @throws exception if at least one file is not chosen.
    * @exception NumberFormatException if port # contains anything but numbers.
    * @exception General exception capture
    */
    private void startProcessingFiles(){
        try{
            String tempIpAddressHolder = txtClientIpAddress.getText();
            //extRegex matches IPv4 address
            String extRegex = "^\\d*\\.\\d*\\.\\d*\\.\\d*$";
            Pattern pattern = Pattern.compile(extRegex);
            Matcher matcher = pattern.matcher(tempIpAddressHolder);
            if (!matcher.find()){
                 /**
                 * If doesn't match, don't throw exception; just allow
                 * user to change if they want. Might be a IPv6 address?
                 */
                int tempHoldJOptionAnswer = JOptionPane.YES_NO_OPTION;
                tempHoldJOptionAnswer = JOptionPane.showConfirmDialog(null,
                        "IP address does not appear "
                        + "to be correct. Would you like to change it?",
                        "IP Address Warning",JOptionPane.YES_NO_OPTION);
                if (tempHoldJOptionAnswer == JOptionPane.YES_NO_OPTION){
                    return;
                }
            }


            /**
            *Throws NumberFormatException if anything but numbers are entered
            *for port.
            */
            int tempPortHolder = Integer.parseInt(txtClientPort.getText());
            setClientControlsToWait();//Disable Start buttons/Counters to 0.
            if (myFileChooser.getSelectedFiles().length <= 0){
                throw new Exception("Must select atleast one file");
            }

            String[] selectedFileNames =
                    new String[myFileChooser.getSelectedFiles().length];
            int counter = 0;
            //Create an array of selected filenames to be processed by DfcClient
            for (File element: myFileChooser.getSelectedFiles()){
                selectedFileNames[counter] = element.toString();
                counter++;
            }
            //Byte counter label is sent so it can be updated by other classes
            myDfcClient = new DfcClient(selectedFileNames, tempIpAddressHolder,
                    tempPortHolder, lblClientBytesSent);
            myDfcClient.addPropertyChangeListener(this);
            myDfcClient.execute(); //Begin Worker Thread

        }catch (NumberFormatException exception) {
            setClientControlsToActive();
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "NumberFormatException Thrown on Client Start Process",
                    JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception) {
            setClientControlsToActive();
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "Exception Thrown on Client Start Process",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /** Stop FileProcessor Threads and DfcClient SwingWorker
    * @TheCs Cohesion - Stops FileProcessor Threads and DfcClient SwingWorker
    * Completeness - Completely stops FileProcessor Threads and DfcClient
    *                SwingWorker.
    * Convenience - Simply stops FileProcessor Threads and DfcClient
    *                SwingWorker.
    * Clarity - It is simple to understand that this stops any FileProcessor
    *           Threads and DfcClient SwingWorker.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception CancellationException for myDfcClient.cancel(true)
    * @exception General exception capture
    */
    private void stopProcessingFiles(){
        try{
            if (myDfcClient != null){
                myDfcClient.myApplication.shutdownNow();//Stop active tasks
                myDfcClient.cancel(true); //Stop SwingWorker

            }

        }catch (CancellationException exception){
            JOptionPane.showMessageDialog(null,"Job Cancelled: " + exception,
                    "Job Cancelled Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,exception,
                    "Exception Thrown on Start Process",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            setClientControlsToActive();//Enable Start buttons
            clientProgressBar.setValue(0);
        }
    }

    /** Create DfcServer object. Begin waiting for data on Server
    * @TheCs Cohesion - Create DfcServer object. wait for data on Server
    * Completeness - Completely creates a DfcServer object. Begins waiting for
    *                data on Server.
    * Convenience - Simply creates a DfcServer object. Begins waiting for
    *                data on Server.
    * Clarity - It is simple to understand that this creates a DfcServer object.
    * Begins waiting for data on Server.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception NumberFormatException if port # contains anything but numbers.
    * @exception General exception capture
    */
    private void startServerListening(){
        try{

            /**
             * myNetworkIp gets the external IP address that would be used for
             * the Server.
             */
            InetAddress myNetworkIp = InetAddress.getLocalHost();
            lblServerActualIp.setText(myNetworkIp.getHostAddress());
            ServerRun = true;//Used by UdpServer to loop, waiting for data.
            setServerControlsToWait();//Disable Start buttons/Byte Counters to 0

            /**
             * NumberFormatException thrown if tempPortHolder contains anything
             * but numbers.
             */
            int tempPortHolder = Integer.parseInt(txtServerPort.getText());     
            /**
             * Labels are passed with DfcServer object to update byte counters
             * and label that tells if waiting or receiving data. Also TextArea
             * is sent as reference to update total counter.
             */
            myDfcServer = new DfcServer(tempPortHolder, txtServerOutput,
                    lblDataCheck, lblServerBytesReceived,
                    lblTotalServerBytesReceived, txtTotalOutput);
            myDfcServer.addPropertyChangeListener(this);
            myDfcServer.execute(); //Begin Worker Thread
        }catch (NumberFormatException exception){
            setServerControlsToActive();
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "NumberFormatException Thrown on Server Start Process",
                    JOptionPane.ERROR_MESSAGE);
        }catch (Exception exception){
            setServerControlsToActive();
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "Exception Thrown on Server Start Process",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            
        }
        
    }

    /** Stop DfcServer/UdpServer from accepting incoming data
    * @TheCs Cohesion - Stops DfcServer/UdpServer from accepting incoming data.
    * Completeness - Completely stops DfcServer/UdpServer from accepting
    *                incoming data.
    * Convenience - Simply creates stops FileProcessor Threads and DfcClient
    *                SwingWorker.
    * Clarity - It is simple to understand that this  stops DfcServer/UdpServer
    *           from accepting incoming data.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception General exception capture
    */
    private void stopServerListening(){
        try{
            //ServerRun to false will tell UdpServer loop to stop
            ServerRun = false;
            if (myDfcServer != null){
                /**
                 * Call myUdpServer.stopIncomingConnections to close the
                 * listening UDP Socket.
                 */
                myDfcServer.myUdpServer.stopIncomingConnections();
            }

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,exception.getMessage(),
                    "Exception Thrown on Server Stop Process",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            setServerControlsToActive();//Enable Start buttons/Byte counter to 0
        }
    }

    /** Detects progress of file processing from DfcClient object.
    * Basically this will determine if the setProgress has been called from
    * the DfcClient object and will increment the progress bar if so.
    * @TheCs Cohesion - Detects progress of file processing from DfcClient
    *                   object.
    * Completeness - Completely detects progress of file processing from
    *                DfcClient object.
    * Convenience - Simply detects progress of file processing from
    *               DfcClient object.
    * Clarity - It is simple to understand that this detects progress of file
    *           processing from ThreadControl object.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param PropertyChangeEvent Determine progress of DfcClient
    */
    public void propertyChange(PropertyChangeEvent evt){
        if ("progress".equals(evt.getPropertyName())){
            int progress = (Integer) evt.getNewValue();
            clientProgressBar.setValue(progress);
            if (progress == 95){//If DfcClient is done.
                setClientControlsToActive();//Enable Start btns/byte counter = 0
                clientProgressBar.setValue(100);
            }
        }
    }

    /** Disables Start btns/Set cursor to wait/Byte Count and Progress to 0
    * @TheCs Cohesion - Disables Start btns/Set cursor to wait/Byte Count and
    *                   Progress to 0.
    * Completeness - Completely disables Start btns/Set cursor to wait/Byte
    *                Count and Progress to 0.
    * Convenience - Simply disables Start btns/Set cursor to wait/Byte
    *                Count and Progress to 0.
    * Clarity - It is simple to understand that this disables Start btns/Set
    *           cursor to wait/Byte Count and Progress to 0.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public void setClientControlsToWait(){
        lblClientBytesSent.setText("0");
        clientProgressBar.setValue(0);
        btnClientStart.setEnabled(false);
        startClientMenuItem.setEnabled(false);
        btnClientCancel.setEnabled(true);
        stopClientMenuItem.setEnabled(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
    /** Enables Start Menu, Start Button,and sets cursor to normal icon.
    * @TheCs Cohesion - Enables Start Menu/Start Button/sets cursor to normal.
    * Completeness - Completely enables Start Menu, Start Button,and sets
    *                cursor to normal icon.
    * Convenience - Simply enables Start Menu, Start Button,and sets cursor
    *               to normal icon.
    * Clarity - It is simple to understand that this enables Start Menu,
    *           Start Button,and sets cursor to normal icon.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public void setClientControlsToActive(){
        btnClientStart.setEnabled(true);
        startClientMenuItem.setEnabled(true);
        btnClientCancel.setEnabled(false);
        stopClientMenuItem.setEnabled(false);
        setCursor(null);
        Toolkit.getDefaultToolkit().beep();
    }

    /** Disable Start Mnu, Start Btn,set counters = 0, progress = indeterminate
    * @TheCs Cohesion - Disable Start Mnu, Start Btn,set counters to 0,
    *                   and progress to indeterminate.
    * Completeness - Completely disables Start Mnu, Start Btn,set counters to 0,
    *                and progress to indeterminate.
    * Convenience - Simply disables Start Mnu, Start Btn,set counters to 0,
    *                and progress to indeterminate.
    * Clarity - It is simple to understand that this disables Start Mnu,
    *           Start Btn,set counters to 0, and progress to indeterminate.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public void setServerControlsToWait(){
        lblServerBytesReceived.setText("0");
        lblTotalServerBytesReceived.setText("0");
        lblDataCheck.setText("*Waiting for Data...");
        serverProgressBar.setIndeterminate(true);
        btnServerStart.setEnabled(false);
        startServerMenuItem.setEnabled(false);
        btnServerStop.setEnabled(true);
        stopServerMenuItem.setEnabled(true);

    }

    /** Enable Start Mnu, Start Btn,set counters = 0, progress != indeterminate
    * @TheCs Cohesion - Enable Start Mnu, Start Btn,set counters to 0, and
    *                   progress to not indeterminate.
    * Completeness - Completely enables Start Mnu, Start Btn,set counters to 0,
    *                and progress to not indeterminate.
    * Convenience - Simply enables Start Mnu, Start Btn,set counters to 0,
    *                and progress to not indeterminate.
    * Clarity - It is simple to understand that this enables Start Mnu, Start
    *           Btn,set counters to 0, and progress to not indeterminate.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public void setServerControlsToActive(){
        lblServerBytesReceived.setText("0");
        lblTotalServerBytesReceived.setText("0");
        lblDataCheck.setText("*Waiting to start...");
        serverProgressBar.setIndeterminate(false);
        btnServerStart.setEnabled(true);
        startServerMenuItem.setEnabled(true);
        btnServerStop.setEnabled(false);
        stopServerMenuItem.setEnabled(false);
        Toolkit.getDefaultToolkit().beep();
    }



    public static boolean ServerRun = true; //Control loop in UdpServer
    DfcServer myDfcServer = null; //Start Server Listening for Data
    DfcClient myDfcClient = null; //Start Client to Send Data
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearServerOutput;
    private javax.swing.JButton btnClientCancel;
    private javax.swing.JButton btnClientStart;
    private javax.swing.JButton btnServerStart;
    private javax.swing.JButton btnServerStop;
    private javax.swing.JMenuItem clientInstructionsMenuItem;
    private javax.swing.JPanel clientPanel;
    private javax.swing.JProgressBar clientProgressBar;
    private javax.swing.JMenuItem copyOutputMenuItem;
    private javax.swing.JMenuItem copyOutputTotalMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbClientBytesLabel;
    private javax.swing.JLabel lblChooseFiles;
    private javax.swing.JLabel lblClientBytesSent;
    private javax.swing.JLabel lblClientIpAddress;
    private javax.swing.JLabel lblClientPort;
    private javax.swing.JLabel lblConnectTo;
    private javax.swing.JLabel lblDataCheck;
    private javax.swing.JLabel lblServerActualIp;
    private javax.swing.JLabel lblServerBytesLabel;
    private javax.swing.JLabel lblServerBytesReceived;
    private javax.swing.JLabel lblServerIpAddress;
    private javax.swing.JLabel lblServerOutput;
    private javax.swing.JLabel lblServerPort;
    private javax.swing.JLabel lblTotalOutputTextArea;
    private javax.swing.JLabel lblTotalServerBytesLabel;
    private javax.swing.JLabel lblTotalServerBytesReceived;
    private javax.swing.JFileChooser myFileChooser;
    private javax.swing.JMenuBar myMenuBar;
    private javax.swing.JMenuItem serverInstructionsMenuItem;
    private javax.swing.JPanel serverPanel;
    private javax.swing.JProgressBar serverProgressBar;
    private javax.swing.JMenuItem startClientMenuItem;
    private javax.swing.JMenuItem startServerMenuItem;
    private javax.swing.JMenuItem stopClientMenuItem;
    private javax.swing.JMenuItem stopServerMenuItem;
    private javax.swing.JTextField txtClientIpAddress;
    private javax.swing.JTextField txtClientPort;
    private javax.swing.JTextArea txtServerOutput;
    private javax.swing.JTextField txtServerPort;
    private javax.swing.JTextArea txtTotalOutput;
    // End of variables declaration//GEN-END:variables

}
