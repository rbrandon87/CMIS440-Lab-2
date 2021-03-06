package russell_cmis440_lab2;


import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane; //For Exception Handling
import java.util.regex.*;


/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/


/** Iterates through a text file to find unique words along w/ count.
* It will stores the results found in a FileResults object and also in a shared
* TotalResults object.
*|----------------------------------------------------------------------------|
*|                          CRC: FileProcessor                                |
*|----------------------------------------------------------------------------|
*|Store Filename                                                DfcClient     |
*|Find/Store Unique Word Length in a Map                        Buffer        |
*|Find/Store Unique Word Length Count in a Map                  FileStats     |
*|                                                                            |
*|----------------------------------------------------------------------------|
*
* @TheCs Cohesion - All methods in this class work together on similar task.
* Completeness - Completely finds unique words in text files and holds count.
* Convenience - There are sufficient methods and variables to complete the
*                required task.
* Clarity - The methods and variables are distinguishable and work in a
*           uniform manner to provide clarity to other programmers.
* Consistency - All names,parameters ,return values , and behaviors follow
*               the same basic rules.
*/


public class FileProcessor implements Runnable{
    String myFileNameString;// Name of File to be counted.
    private final Buffer mySharedBuffer;//Shared object.
    FileStats myFileStats;//FileStats object for one file.
    private String myTempLineHolder = "";// Hold line input from file being read
    String myWordDelimiter = "\\p{Zs}"; //Default is a space
    Scanner readInputFile = null;

    /**All data fields of the FileProcessor class are initialized here.
    * @TheCs Cohesion - All data fields of FileProcessor class initialized here.
    * Completeness - Completely all data fields of the FileProcessor class
    *                are initialized here.
    * Convenience - Simply all data fields of the FileProcessor class
    *               are initialized here.
    * Clarity - It is simple to understand that this all data fields of the
    *           FileProcessor class are initialized here.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public FileProcessor(String afileNameString,Buffer aSharedBuffer){
        myFileNameString = afileNameString;
        mySharedBuffer = aSharedBuffer;
        /**
         * Creates a new FileStats Buffer with given filename. This will hold
         * all the data from the file being processed here.
         */
        myFileStats = new FileStats(myFileNameString);

    }

    /** Open a file to count unique word length/count & stores this Data
    * mySharedBuffer is synchronized since it is a shared object.
    * @TheCs Cohesion - Open a file to count unique word length/count &
    *                   stores this Data
    * Completeness - Completely opens a file to count unique word length/count
    *                & stores this Data
    * Convenience - Simply opens a file to count unique word length/count
    *               & stores this Data
    * Clarity - It is simple to understand that this open a file to count
    *           unique word length/count & stores this Data
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @exception FilNotFound Exception for file input
    * @exception IOException for file input
    * @exception Exception any unexpected exceptions thrown by Thread/Sync ops.
    */
    public void run(){
        try{

            /**
             * Creates new Scanner object with give file and use the \\z
             * delimiter to read the entire file into the myTempLineHolder
             * variable when the .next method is called.
             */
            readInputFile = new Scanner(
                    new File(myFileNameString));
            readInputFile.useDelimiter("\\z");
            myTempLineHolder = readInputFile.next();
            /**
             * Since the entire file is read into the variable I will close the
             * file here since it is no longer needed.
             */
            if (readInputFile != null){//Make sure it isn't null first
                readInputFile.close();//File no longer needed, close it.
            }

            /**
             * This regex will match against the entire file and will attempt
             * to find a word, a word being any alphabetic character plus an
             * apostophe, and will place this word into a group. A loop will
             * then go through all the groups and if their length is not zero
             * the length will be added to the FileStats object for this file.
             */
            String extRegex = "([\\p{Alpha},\\']*)";
            Pattern pattern = Pattern.compile(extRegex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(myTempLineHolder);
            while (matcher.find()){
                if (matcher.group(1).length() != 0){
                    myFileStats.addWordToMap(matcher.group(1).length());
                    
                }

            }


            mySharedBuffer.set(myFileStats);//Update Shared Buffer 


        }catch(FileNotFoundException exception){
            JOptionPane.showMessageDialog(null, "File not found exception.\n"
                    + exception.getMessage(),
                    "FileNotFoundException Thrown", JOptionPane.ERROR_MESSAGE);
        }catch(IOException exception){
            JOptionPane.showMessageDialog(null, "I/O exception thrown.\n"
                    + exception.getMessage(),
                    "IOException Thrown", JOptionPane.ERROR_MESSAGE);
        }catch(Exception exception){
            JOptionPane.showMessageDialog(null, "Unknown exception thrown.\n"
                    + exception.getMessage(),
                    "Exception Thrown", JOptionPane.ERROR_MESSAGE);
        }

    }

    /** Retrieves the file information stored in the FileStats object.
    * @TheCs Cohesion - Retrieves the file information stored in the FileStats
    *                   object.
    * Completeness - Completely retrieves the file information stored in the
    *                FileStats object.
    * Convenience - Simply retrieves the file information stored in the
    *               FileStats object.
    * Clarity - It is simple to understand that this retrieves the file
    *           information stored in the FileStats object.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public FileStats getFileResults(){
        return myFileStats;
    }

    /** This method takes the Filename, which is unique, multiplies it by 17,
    * a prime number, and creates a unique hashCode used to test for equality
    * @TheCs Cohesion - This method takes the Filename, which is unique,
    *                   multiplies it by 17, a prime number, and creates a
    *                   unique hashCode used to test for equality.
    * Completeness - Completely takes the Filename, which is unique,
    *                multiplies it by 17, a prime number, and creates a unique
    *                hashCode used to test for equality.
    * Convenience - Simply takes the Filename, which is unique, multiplies it
    *               by 17, a prime number, and creates a unique hashCode used
    *               to test for equality.
    * Clarity - It is simple to understand that this takes the Filename, which
    *           is unique, multiplies it by 17, a prime number, and creates
    *           a unique hashCode used to test for equality.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    @Override
    public int hashCode(){
        return 17 * this.getFileResults().getFileName().hashCode();
    }

    /**
    * This method first checks to see if the object in question is null and
    * if so returns false. It then test to see if the objects refer to the same
    * object and returns true if they do. It then checks to see if obj is an
    * instance of FileProcessor and returns false if it is not or
    * if so it then cast the object to an FileProcessor object and test the
    * hashCode of each object and returns true or false depending on if the
    * hashCodes are equal or not.
    * @TheCs Cohesion - Overrides the equals method. Test for equality of two
    *                   objects.
    * Completeness - Completely test for equality of two objects.
    * Convenience -  Simply test for equality of two objects.
    * Clarity - It is simple to understand that this test for equality of two
    *           FileProcessor objects.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    * @param obj Check for equality against this object.
    * @precondition obj be a FileProcessor object, otherwise return false.
    */
    @Override
    public boolean equals(Object obj){

        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof FileProcessor){
            FileProcessor other = (FileProcessor) obj;
            return this.hashCode() == other.hashCode();
        }else{
            return false;
        }

    }

}


