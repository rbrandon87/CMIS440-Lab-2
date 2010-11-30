package russell_cmis440_lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import javax.swing.SwingWorker; //Worker Thread to free up GUI Thread
import javax.swing.JOptionPane; //For Exception Handling
import java.util.concurrent.ExecutionException;
import javax.swing.JTextArea;
import javax.swing.JLabel;

/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** Creates an instance of UdpServer with the specified port.
* Basically this class is a SwingWorker from the LabMainWindow GUI that takes
* a port number, two TextAreas, and three labels and creates an instance of
* the UdpServer class with this information.
*|----------------------------------------------------------------------------|
*|                           CRC: DfcServer                                   |
*|----------------------------------------------------------------------------|
*|Create an instance of UdpServer on it's on Thread              UdpServer    |
*|                                                                            |
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





public class DfcServer extends SwingWorker<Void, Void> {

    ExecutorService myApplication = null;
    UdpServer myUdpServer = null;

    /** Constructor for DfcServer; initialize UdpServer and Executor objects
    * @TheCs Cohesion - Constructor for DfcServer; initialize UdpServer and
    *                   Executor objects.
    * Completeness - Completely creates constructs for DfcServer; initialize
    *                UdpServer and Executor objects.
    * Convenience - Simply creates constructs for DfcServer; initialize
    *                UdpServer and Executor objects.
    * Clarity - It is simple to understand that this creates constructs for
    *           DfcServer; initialize UdpServer and Executor objects.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param aServerPort port number for server
    * @param aJOutputTextArea TextArea to display file information
    * @param aDataCheckLabel label shows data receiving or not
    * @param aBytesReceivedLabel label shows bytes received per send
    * @param aTotalBytesReceivedLabel label show total bytes received
    * @param aTxtTotalOutput TextArea show file information totals
    */
    public DfcServer(int aServerPort, JTextArea aJOutputTextArea, 
            JLabel aDataCheckLabel, JLabel aBytesReceivedLabel,
            JLabel aTotalBytesReceivedLabel, JTextArea aTxtTotalOutput){
        /**
         * Create new UdpServer object with port and GUI references
         * Create Executor object to run UdpServer object in its own thread
         */
        myUdpServer = new UdpServer(aServerPort, aJOutputTextArea, 
                aDataCheckLabel, aBytesReceivedLabel, aTotalBytesReceivedLabel,
                aTxtTotalOutput);
        myApplication = Executors.newCachedThreadPool();
    }

    /** Add myUdpServer to Executor ThreadPool and execute.
    * Overrides doInBackground method of SwingWorker class.
    * @TheCs Cohesion - Add myUdpServer to Executor ThreadPool and execute.
    * Completeness - Completely adds myUdpServer to Executor ThreadPool
    *                and execute.
    * Convenience - Simply adds myUdpServer to Executor ThreadPool
    *                      and execute.
    * Clarity - It is simple to understand that this adds myUdpServer to
    *           Executor ThreadPool and execute.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception RejectedExecutionException for Executor Thread ops.
    * @exception NullPointerException for Executor Thread ops.
    * @exception Exception general capture
    */
    @Override
    public Void doInBackground(){
        try{
            /**
             * Add myUdpServer object to myApplication Executor Thread Pool and
             * execute, then immediately call shutdown so the current thread
             * will run, but no other new ones can be made.
             */
            myApplication.execute(myUdpServer);
            myApplication.shutdown();

        }catch(RejectedExecutionException exception){
            JOptionPane.showMessageDialog(null,"Rejected Execution Exception "
                    + "on Executor thread run.\n" + exception.getMessage(),
                    "Rejected Execution Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch(NullPointerException exception){
            JOptionPane.showMessageDialog(null,"Null Pointer Exception on "
                    + "Executor run.\n" + exception.getMessage(),
                    "Null Pointer Exception",
                    JOptionPane.ERROR_MESSAGE);
        }catch(Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception on thread run"
                    + ".\n" + exception.getMessage(),
                    "Exception",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return null;
        }


    }


    /** Notifies main window that SwingWorker worker thread is done.
    * Overrides done method in SwingWorker class.
    * @TheCs Cohesion - Notifies main window SwingWorker worker thread is done.
    * Completeness - Completely Notifies main window that SwingWorker worker
    *                thread is done.
    * Convenience - Simply Notifies main window that SwingWorker worker thread
    *               is done.
    * Clarity - It is simple to understand that this Notifies main window that
    *           SwingWorker worker thread is done.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception InterruptedException for call to get()
    * @exception ExecutionException  for call to get()
    */
    @Override
    public void done(){
        try {
            get();
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (final ExecutionException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }



}
