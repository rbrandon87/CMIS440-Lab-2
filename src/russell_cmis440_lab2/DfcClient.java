package russell_cmis440_lab2;



import java.util.ArrayList; //To create an ArrayList of WordCounter objects
import java.io.File;//To retrieve the text files in the current directory.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilenameFilter;//To filter out all files, but text files.
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker; //Worker Thread to free up GUI Thread
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane; //For Exception Handling
/**
* Program Name: CMIS440 Lab 1 Word Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 15, 2010
*/

/** This class creates WordCounter objects to process text files and count words
* This class will create an ArrayList of WordCounters and an Array of Threads to
* run each of them. Once complete it will write the results to the output text
* file. It utilizes the SwingWorker worker thread methods injunction with the
* main calling class in order to not freeze the GUI and also to update the
* progress bar.
*|----------------------------------------------------------------------------|
*|                           CRC: ThreadControl                               |
*|----------------------------------------------------------------------------|
*|Create shared Total results object                             TotalResults |
*|                                                               FileResults  |
*|Run Threads for counting txt file lines/words                  WordCounter  |
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

public class DfcClient extends SwingWorker<Void, Void>{

    ArrayList<FileProcessor> fileProcessors;//hold WordCounter objs.
    Buffer mySharedBuffer;//Shared TotalResults object.
    FileStatsProcessor myFileStatsProcessor = null;
    String[] myFileNames;//String of Filenames in currect directory.
    File myCurrentFolder;//Will hold the name of the current directory.
    Thread[] wordCounterThreads;// Threads for use by WordCounter instances.
    FilenameFilter aFilter;//to filter out everything, but text files.
    String myIndividualFileResults = "";
    int myNumOfFiles = 0; //Number of files being processed.
    ExecutorService myApplication = null;

    /** Constructor initializes variables and creates array of filenames.
    * @TheCs Cohesion - initializes variables/creates array of filenames.
    * Completeness - Completely initializes variables/creates array of filenames
    * Convenience - Simply initializes variables/creates array of filenames.
    * Clarity - It is simple to understand that this initializes
    *           variables/creates array of filenames.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param fileArguments contains filenames or name directory of files
    * @param aDelimiter will give user specified delimiter
    * @param aCaseCheck will give user specified case sensitive check
    * @exception exception if none or other than text files are given.
    */
    public DfcClient(String[] fileArguments, String aServerIpAddress, int aServerPort) throws Exception{
        try{

            aFilter = new FilenameFilter(){
                public boolean accept(File dir, String name){
                    return name.endsWith("txt");//Filter only text files.
                }
            };
            if (fileArguments.length == 1){
                File tempDirCheck = new File( fileArguments[0]);
                if (tempDirCheck.isDirectory()){
                    myFileNames = tempDirCheck.list(aFilter);
                    for(int i = 0; i< myFileNames.length; i++){
                        myFileNames[i] = tempDirCheck.toString()
                                + File.separator + myFileNames[i];
                    }
                }else{
                    myFileNames = fileArguments;//Specified filesnames from args
                }
            }else{
                myFileNames = fileArguments;//Specified filesnames from args.
            }

            myNumOfFiles = myFileNames.length;

            for(int i=0; i< myFileNames.length; i++){
                if(!myFileNames[i].endsWith("txt")){
                    throw new Exception("Only process files ending in " +
                            "txt(Text Files).");
                }

            }

            if (myFileNames.length <= 0){
                throw new Exception("Must Specify atleast one text file.");
            }
            fileProcessors = new ArrayList<FileProcessor>();
            mySharedBuffer = new Buffer();
            myFileStatsProcessor = new FileStatsProcessor(aServerIpAddress, aServerPort, mySharedBuffer, getNumOfFiles());
            myApplication = Executors.newCachedThreadPool();
        }catch(Exception exception){
            throw new Exception(exception);
        }
    }

    /** Returns an ArrayList of WordCounter objects for printing toString method
    * @TheCs Cohesion - Returns an ArrayList of WordCounter objects for printing
    *                   toString method
    * Completeness - Completely returns an ArrayList of WordCounter objects
    *                for printing toString method
    * Convenience - Simply returns an ArrayList of WordCounter objects
    *               for printing toString method
    * Clarity - It is simple to understand that this Returns an ArrayList of
    *           WordCounter objects for printing toString method
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public ArrayList<FileProcessor> getWordCountersList(){
        return fileProcessors;
    }

    /** Returns TotalResults obj shared by WordCounter objs to print toString
    * @TheCs Cohesion - Returns TotalResults object shared by WordCounter
    *                   objects for printing toString method.
    * Completeness - Completely returns TotalResults object shared by WordCounter
    *                objects for printing toString method.
    * Convenience - Simply returns TotalResults object shared by WordCounter
    *                   objects for printing toString method.
    * Clarity - It is simple to understand that this returns TotalResults
    *           object shared by WordCounter objects for printing toString
    *           method.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public Buffer getTotalResults(){
        return mySharedBuffer;
    }

    /** Create threads to process text files and then writes to output file.
    * Overrides doInBackground method of SwingWorker class.
    * @TheCs Cohesion - Create threads;process text files/writes to output file.
    * Completeness - Completely create threads to process text files and
    *                then writes to output file.
    * Convenience - Simply Create threads to process text files and then writes
    *               to output file.
    * Clarity - It is simple to understand that this Create threads to process
    *           text files and then writes to output file.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception InterruptedException for Thread ops.
    * @exception FileNotFoundException for output file writing
    * @exception IOException for output file writing
    * @exception Exception general capture
    */
    @Override
    public Void doInBackground() throws Exception{
        try{
            int progress = 0;
            setProgress(0); //Updates progress bar on main window

            wordCounterThreads = new Thread[myFileNames.length];
            for(int i=0; i< myFileNames.length; i++){
                myApplication.execute( new FileProcessor(myFileNames[i], mySharedBuffer));
            }
                myApplication.execute(myFileStatsProcessor);

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
            setProgress(95);
            get();
        } catch (final InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (final ExecutionException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    /** Returns the number of files being processed to display on output
    * @TheCs Cohesion - Returns # of files processed to display on output.
    * Completeness - Completely returns the number of files being processed
    *                to display on output.
    * Convenience - Simply returns the number of files being processed to
    *               display on output.
    * Clarity - It is simple to understand that this Returns the number of files
    *           being processed to display on output.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    private String getNumOfFiles(){
        return Integer.toString(myNumOfFiles);
    }


}
