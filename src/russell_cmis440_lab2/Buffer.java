package russell_cmis440_lab2;

import java.util.concurrent.ArrayBlockingQueue;

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





public class Buffer {

    private final ArrayBlockingQueue mySharedBuffer;

    public Buffer(){
        mySharedBuffer = new ArrayBlockingQueue<FileStats>(100);
    }

    public void set( FileStats newFileStatBuffer){
        try{
            mySharedBuffer.put(newFileStatBuffer);
        }catch (InterruptedException exception){

        }

    }

    public FileStats get(){
        FileStats tempFileStatsHolder = null;
        try{
            tempFileStatsHolder = (FileStats) mySharedBuffer.take();
            return tempFileStatsHolder;
        }catch (InterruptedException exception){
            return null;

        }finally{
            
        }


    }

    public int getBufferSize(){
        try{
            return (mySharedBuffer.size() - mySharedBuffer.remainingCapacity());
        }catch (Exception exception){
            return 0;

        }finally{

        }
        
    }

}
