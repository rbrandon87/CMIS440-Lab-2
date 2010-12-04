package russell_cmis440_lab2;


/**
* Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
* @author Brandon R Russell
* @Course CMIS440
* Date: Nov 19, 2010
*/

/** The Interface that defines the methods of the Buffer class.
*|----------------------------------------------------------------------------|
*|                                CRC: IBuffer                                |
*|----------------------------------------------------------------------------|
*|Defines methods that must be used in the Buffer class               Buffer  |
*|----------------------------------------------------------------------------|
*
* @TheCs Cohesion - All methods in this class work together on similar task.
* Completeness - Completely defines interface for WordCounter class.
* Convenience - There are sufficient methods and variables to complete the
*                required task.
* Clarity - The methods and variables are distinguishable and work in a
*           uniform manner to provide clarity to other programmers.
* Consistency - All names,parameters ,return values , and behaviors follow
*               the same basic rules.
*/



public interface IBuffer {

    /**Puts a new FileStats object onto the end of the Buffer
    * @TheCs Cohesion - Puts a new FileStats object onto the end of the
    *                   Buffer.
    * Completeness - Completely puts a new FileStats object onto the end of
    *                the Buffer.
    * Convenience - Simply puts a new FileStats object onto the end of the
    *               Buffer.
    * Clarity - It is simple to understand that this puts a new FileStats object
    *           onto the end of the Buffer.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public void set( FileStats newFileStatBuffer);

    /**Takes a FileStats object from the front of the Buffer
    * @TheCs Cohesion - Takes a FileStats object from the front of the
    *                   Buffer.
    * Completeness - Completely takes a FileStats object from the front of the
    *                Buffer.
    * Convenience - Simply takes a FileStats object from the front of the
    *               Buffer.
    * Clarity - It is simple to understand that this takes a FileStats object
    *           from the front of the Buffer.
    * Consistency - It uses the same syntax rules as the rest of the class and
    *               continues to use proper casing and indentation.
    */
    public FileStats get();

    

}
