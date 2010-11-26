package russell_cmis440_lab2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingWorker; //Worker Thread to free up GUI Thread
import javax.swing.JOptionPane; //For Exception Handling
import java.util.concurrent.ExecutionException;

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





public class DfcServer extends SwingWorker<Void, Void> {

    ExecutorService myApplication = null;
    UdpServer myUdpServer = null;

    public DfcServer(int aServerPort){
        myUdpServer = new UdpServer(aServerPort);
        myApplication = Executors.newCachedThreadPool();
    }

    @Override
    public Void doInBackground() throws Exception{
        try{
            myApplication.execute(myUdpServer);
            myApplication.shutdown();

        }catch(Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception on thread run"
                    + ".\n" + exception.getMessage(),
                    "Exception",
                    JOptionPane.ERROR_MESSAGE);
        }finally{
            return null;
        }


    }

    @Override
    public void done(){
        try {
            setProgress(95);
            get();
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (final ExecutionException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }


}
