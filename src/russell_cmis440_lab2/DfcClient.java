package russell_cmis440_lab2;



import java.io.File;//To retrieve the text files in the current directory.
import java.io.FilenameFilter;//To filter out all files, but text files.
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker; //Worker Thread to free up GUI Thread
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane; //For Exception Handling
import javax.swing.JLabel; //For Passing reference to FileStatsProcessor

/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** This class creates FileProcessor objects to process txt files/count word len
* This class will create an Executor Service of FileProcessors and run each of
* them. It also creates a FileStatsProcessor that will send the data on the txt
* files to a specified Server. It utilizes the SwingWorker worker thread
* methods injunction with the main calling class in order to not freeze the GUI
* and also to update the progress bar and bytes sent counter.
*|-----------------------------------------------------------------------------|
*|                                   CRC: DfcClient                            |
*|-----------------------------------------------------------------------------|
*|Create shared Buffer object                                Buffer            |
*|                                                           FileStatsProcessor|
*|Run Executor Services for counting txt file word lengths   FileProcessor     |
*|-----------------------------------------------------------------------------|
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

    Buffer mySharedBuffer;//Shared TotalResults object.
    FileStatsProcessor myFileStatsProcessor = null;
    String[] myFileNames;//String of Filenames.
    FilenameFilter aFilter;//to filter out everything, but text files.
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
    * @param aServerIpAddress contains Server IP address
    * @param aServerPort contains Server Port
    * @param alblClientBytesSent Reference to GUI byte sent counter label
    * @exception exception if none or other than text files are given.
    */
    public DfcClient(String[] fileArguments, String aServerIpAddress,
            int aServerPort, JLabel alblClientBytesSent) throws Exception{
        try{

            aFilter = new FilenameFilter(){
                /**
                 * This simply sets up a filter so that only files ending in
                 * txt are processed.
                 */
                public boolean accept(File dir, String name){
                    return name.endsWith("txt");
                }
            };
            /**
             * Below, this checks to see if only one argument is sent in the
             * fileArguments array. If so, it then checks to see if this is a
             * directory. If it is, it uses the Filter from above and searches
             * all the files in the directory for text files and adds them to
             * an array of filenames to be processed. If the length of the
             * arguments is not one or if it is not a directory then the given
             * arguments are passed directly to the filename array.
             */
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

            myNumOfFiles = myFileNames.length;//Store Num Of files for later use

            for(int i=0; i< myFileNames.length; i++){
                /**
                 * Checks to make sure all files in filename array end in.txt
                 */
                if(!myFileNames[i].endsWith("txt")){
                    throw new Exception("Only process files ending in " +
                            "txt(Text Files).");
                }

            }

            if (myFileNames.length <= 0){
                throw new Exception("Must Specify atleast one text file.");
            }

            mySharedBuffer = new Buffer();//Will Hold FileStat objects
            myFileStatsProcessor = new FileStatsProcessor(aServerIpAddress, 
                    aServerPort, mySharedBuffer, getNumOfFiles(),
                    alblClientBytesSent);
            myApplication = Executors.newCachedThreadPool();
        }catch (Exception exception) {
            throw new Exception(exception);
        }
    }


    /** Create Executor to start FileProcessor objects. Notify GUI when complete
    * Overrides doInBackground method of SwingWorker class.
    * @TheCs Cohesion - Create Executor to start FileProcessor objects.
    *                   Notify GUI when complete.
    * Completeness - Completely creates Executor to start FileProcessor objects.
    *                Notify GUI when complete.
    * Convenience - Simply creates Executor to start FileProcessor objects.
    *               Notify GUI when complete.
    * Clarity - It is simple to understand that this creates an Executor to
    *           start FileProcessor objects. Notify GUI when complete.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception RejectedExecutionException for Executor Thread ops.
    * @exception NullPointerException for Executor Thread ops.
    * @exception Exception general capture
    */
    @Override
    public Void doInBackground(){
        try{
            int progress = 0;
            setProgress(0); //Updates progress bar on main window
            /**
             * Start myFileStatsProcessor thread first. It will hold on the
             * first take until the FileProcessor threads start executing puts.
             */
            myApplication.execute(myFileStatsProcessor);

            /**
             * Loop through and create a new FileProcessor for each file to
             * process on its own thread.
             */
            for(progress=0; progress< myFileNames.length; progress++){
                myApplication.execute( new FileProcessor(myFileNames[progress],
                                                               mySharedBuffer));
                /**
                 * For the setProgress method below, it basically uses Math.min
                 * to return the smallest number between the variable 'progress'
                 * and 90.
                 */
                setProgress(Math.min(progress, 90));//Update GUI progress bar.
            }

                setProgress(94);//Update GUI progress bar.
                /**
                 * the shutdown method below will make sure no new task are
                 * executed, but the ones already started will be allowed to
                 * finish.
                 */
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
            /**
             * The doInBackground method here doesn't really return anything,
             * other than what the setProgress() method returns so I just have
             * this get() method here for completion.
             */
            setProgress(95);//Update GUI progress bar.
            get();
        } catch (final InterruptedException ex) {
            JOptionPane.showMessageDialog(null,"Interrupted Exception on thread"
                    + " done.\n" + ex.getMessage(),
                    "Interrupted Exception",
                    JOptionPane.ERROR_MESSAGE);
        } catch (final ExecutionException ex) {
            JOptionPane.showMessageDialog(null,"Execution Exception on thread"
                    + " done.\n" + ex.getMessage(),
                    "Execution Exception",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Returns the number of files being processed
    * @TheCs Cohesion - Returns number of files being processed .
    * Completeness - Completely returns the number of files being processed.
    * Convenience - Simply returns the number of files being processed.
    * Clarity - It is simple to understand that this returns the number of files
    *           being processed.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception Exception general exception capture
    */
    private String getNumOfFiles(){
        try{
            /**
             * This is mainly used by passing it to the FileStatsProcessor
             * which uses it for GUI printing purposes, but also to determine
             * the number iterations to go through when sending bytes to the
             * server.
             */
            return Integer.toString(myNumOfFiles);
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Unknown Exception on "
                    + "number of files returned.\n" + exception.getMessage(),
                    "Number Format Exception",
                    JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

}
