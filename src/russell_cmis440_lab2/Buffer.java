package russell_cmis440_lab2;

import java.util.concurrent.ArrayBlockingQueue;
import javax.swing.JOptionPane; //For Exception Handling


/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** Creates an ArrayBlockingQueue of FileStats. Buffer between client and server
* Basically this class creates an ArrayBlockingQueue of FileStats with a length
* of 10. I tested different array lengths and decided 10 was the best number
* to use. There is only a set and get method here that either puts a FileStats
* object onto the end of the Array or takes one from the front.
*|----------------------------------------------------------------------------|
*|                           CRC: Buffer                                      |
*|----------------------------------------------------------------------------|
*|Initially created and passed as reference              DfcClient            |
*|Fill buffer with FileStats object                      FileProcessor        |
*|Send FileStats object from Buffer to Server            FileStatsProcessor   |
*|                                                       IBuffer              |
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




public class Buffer implements IBuffer {

    private final ArrayBlockingQueue<FileStats> mySharedBuffer;
    private final int MYARRAYSIZE = 10;
    /**Buffer Constructor;initialize ArrayBlockingQueue
    * @TheCs Cohesion - Buffer Constructor;initialize ArrayBlockingQueue.
    * Completeness - Completely constructs buffer;initializes ArrayBlockingQueue
    * Convenience - Simply constructs buffer;initializes ArrayBlockingQueue.
    * Clarity - It is simple to understand that this is the buffer Constructor
    *           ;initializes ArrayBlockingQueue.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public Buffer(){
        
        mySharedBuffer = new ArrayBlockingQueue<FileStats>(MYARRAYSIZE);
        
    }

    /**Puts a new FileStats object onto the end of the ArrayBlockingQueue
    * @TheCs Cohesion - Puts a new FileStats object onto the end of the
    *                   ArrayBlockingQueue.
    * Completeness - Completely puts a new FileStats object onto the end of
    *                the ArrayBlockingQueue.
    * Convenience - Simply puts a new FileStats object onto the end of the
    *               ArrayBlockingQueue.
    * Clarity - It is simple to understand that this puts a new FileStats object
    *           onto the end of the ArrayBlockingQueue.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param newFileStatBuffer a new FileStats object to put in queue.
    * @exception InterruptedException if thread is interrupted during put
    * @exception Exception general capture exception
    */
    public void set( FileStats newFileStatBuffer){
        try{
            mySharedBuffer.put(newFileStatBuffer);
        }catch (InterruptedException exception){
            /**
             * This should only occur when the user clicks the cancel button, so
             * I did not put a JOptionPane here; otherwise it would produce
             * one for potentially many files.
             */
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception "
                    + "on Buffer set.\n"
                    + exception.getMessage(),"Unknown Exception",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    /**Takes a FileStats object from the front of the ArrayBlockingQueue
    * @TheCs Cohesion - Takes a FileStats object from the front of the
    *                   ArrayBlockingQueue.
    * Completeness - Completely takes a FileStats object from the front of the
    *                ArrayBlockingQueue.
    * Convenience - Simply takes a FileStats object from the front of the
    *               ArrayBlockingQueue.
    * Clarity - It is simple to understand that this takes a FileStats object
    *           from the front of the ArrayBlockingQueue.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception InterruptedException if thread is interrupted during take
    * @exception Exception general capture exception
    */
    public FileStats get(){
        FileStats tempFileStatsHolder = null;
        try{
            tempFileStatsHolder = mySharedBuffer.take();
            return tempFileStatsHolder;
        }catch (InterruptedException exception){
            /**
             * This should only occur when the user clicks the cancel button, so
             * I did not put a JOptionPane here; otherwise it would produce
             * one for potentially many files.
             */
            return null;
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception "
                    + "on Buffer get.\n"
                    + exception.getMessage(),"Unknown Exception",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }


}
