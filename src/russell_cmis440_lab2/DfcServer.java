package russell_cmis440_lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingWorker; //Worker Thread to free up GUI Thread

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





public class DfcServer {

    ExecutorService myApplication = null;
    UdpServer myUdpServer = null;

    public DfcServer(int aServerPort){
        myUdpServer = new UdpServer(aServerPort);
        myApplication = Executors.newCachedThreadPool();
    }



}
