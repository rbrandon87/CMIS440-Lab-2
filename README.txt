Program Name: CMIS440 Lab 2 Client/Server Word Length Counter
Author Brandon R Russell
Course CMIS440
Date: Nov 19,2010
IDE: NetBeans 6.9.1
OS: Windows 7
Java: JDK 1.6.0_22
Files: LabMainWindow.java, DfcServer.java, UdpServer.java,
        FileStatsProcessor.java, Buffer.java, FileProcessor.java,
        FileStats.java, DfcClient.java, IBuffer.java

Program Requirements:
Client:
Must explicitly handle all file related,network related, thread related
exceptions as a minimum.
File statistics class named FileStats that has getter / setter methods for file
name and a Map with a key of type Integer that represents the length of the
word and the value of type Integer that represents the count of words of that
length; and an overridden toString method that formats the filename and Map.
A shared buffer class named Buffer that uses the ArrayBlockingQueue class
implemented with the FileStats class.
A file processing class named FileProcessor (this is the Producer) that takes
the file name to be processed and the shared buffer instance in the
constructor and whose 'run' method will process the text file by extracting
all the words, where a word is defined as any sequence of alphabetic characters
plus the apostophe, i.e., given: the quick brown fox's collar has 4 buttons;
there are 7 words: the, quick, brown, fox's, collar, has, buttons. Using the
extracted words you will keep count of the number of words at each length
, i.e., using the above example, you would update the Map entry for 3 after
processing the word 'the', update the Map entry 5 after processing the words
'quick' and 'brown', etc. After processing the file, you will add the final
FileStats instance to the shared buffer.
A FileStats processing class named FileStatsProcessor (this is the Consumer)
that takes the IP address or host name of the Server application,
the port number and the shared buffer instance in the constructor. The 'run'
method will create a Datagram socket for sending the FileStats then extract
FileStats instances from the shared buffer as they become available and format
the FileStats instance using its toString method and send the resulting bytes
to the server.
A distributed file counter client class named DfcClient whose 'main' method
will create the shared buffer, create an instance of the ExecutorService and
create the FileStatsProcessor and add to this ExecutorService instance; get a
listing of all the files in a chosen directory, create a separate instance of
the Executor and then for each file in the chosen directory create an instance
of FileProcessor and add to the ExecutorService instance, wait for all the
FileProcessor instances to finish and then terminate the client application.
Server:
Must explicitly handle all network related, thread related exceptions as a
minimum.
A UDP port monitor class named UdpServer that takes a port in the constructor
and whose 'run' method opens the UDP socket on the given port and prints all
received datagrams to the console as strings.
A distributed file counter server class named DfcServer whose 'main' method
will create an instance of UdpServer and place it in its own Thread and then
wait indefinitely (we are not going to worry about gracefully shutting
down the server).
Assignment Optional Implementations:
Create a GUI for the Client and / or Server application.
Enhance the individual file statistics gathered and sent to the server.
Enhance the server side processing of the statistics to hold running totals
, averages, etc.
Add a simple menu to the server that accepts a 'quit' command that gracefully
will stop the UdpServer instance and terminate the application.

Program Design: I designed this program so one can, type in a IP address/Port
on the client side, choose one or more files, or an entire directory, click
the start button and have the server side receive the data. On the Server side
one only needs to specify the port to listen on, then click the 'Start
Listening' button and then wait to receive input. For testing purposes, one
can designate the IP address of the server, on the client side, as localhost.


Things you what me to know before I grade your work: I used the NetBeans
Graphic designer to create the GUI portion of this program. Also, in the
FileStatsProcessor class I made use of Thread.sleep. In NetBeans it has a
warning associated with it because of possible performance problems, however
I feel it is necessary to have to ensure data doesn't get lost.


Git Log
--------
commit 807b9d440b83db6808d42f9b12f7684d59f53474
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Thu Dec 9 20:39:55 2010 -0100

    Minor stuff to comments. More debugging. Everything is checking good. Updated README and UML

commit 0d66d46748e221ca032e517e17224b8385b5a1f6
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Wed Dec 8 20:39:05 2010 -0100

    Added more comments. Checked exception handling, tested program more, everything is good

commit 36c84a666e56dc98e305f3dbac6e85ca7a0818de
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Mon Dec 6 21:03:10 2010 -0100

    Did some testing/debugging. Minor comment fixes. Generated JavaDoc and Fresh Build

commit 80bee29c4c73392110a5d8c9a431ccbfa3974e45
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sun Dec 5 20:25:57 2010 -0100

    Minor UI corrections and comment changes

commit c9edc28dc4cd57e7683de6cef0550c6d91b2db9a
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sat Dec 4 17:44:18 2010 -0100

    Generated JavaDocs, Updated README, Updated UML

commit b328f7951b7777d46e8086c3aea19841abdae664
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sat Dec 4 17:29:45 2010 -0100

    Fixed color scheme, tab order, and tooltips for GUI, as well as started using Nimbus Look and Feel. Fixed Interrupted Exception problem when user clicked the cancel button on the client side.

commit b5ea4f0b20d765ea1599ac2eb5173dfb456dbaad
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sat Dec 4 16:21:21 2010 -0100

    Finished all comments and exception handling.

commit 13fdd9b627de347d8a91a67d0ff4675e00a62dfd
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Dec 3 23:29:44 2010 -0100

    Determined 576 best size for byte array for sending udp packets

commit 4e178d4d4b016c463cdc9fd8e3bccfa6b7ef2ed2
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Dec 3 23:10:19 2010 -0100

    Tested/Updated ArrayBlockingQueue size to 10. Added constants for random gen and packet size. Comments/Exception for FileProcessor done.

commit 179b48b97412bbc83722dafed97b45fa8edec34d
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Thu Dec 2 18:10:12 2010 -0100

    Added Comments/Exception Handling to Buffer class.

commit 37df2b865af31d6b1e90ac96963acc4588bcb4e3
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Wed Dec 1 21:01:04 2010 -0100

    FileStatsProcessor Comments and Exception done. Fixed Buffer warning.

commit 779f9121b3d70becfcf3883b025c22ee34aa548a
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Tue Nov 30 21:28:41 2010 -0100

    Added IBuffer interface

commit 89167b2f25f47fc6c425b2db00fae33fb370a6cd
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Tue Nov 30 21:19:52 2010 -0100

    DfcServer and UdpServer comments/exceptions done.

commit f89316d35d483c2387fa347a746b762fa15028cb
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Mon Nov 29 22:44:08 2010 -0100

    Comments/Exceptions done for LabMainWindow.java

commit e405e0b227267b8f6efbc63b9dd84df32927704a
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Mon Nov 29 07:30:52 2010 -0100

    Minor stuff to byte counters. Added separate client/server instructions

commit 3f1b2a52ad63f05b44d5a0e6c558c644e92a6cee
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sun Nov 28 22:04:21 2010 -0100

    Added byte counters. gui work. Still got a lot of work to do before dec 10

commit 1ff34563cc693921431e8cf7efdda782fcb2f1a6
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sun Nov 28 18:45:19 2010 -0100

    Everything is working. GUI mods. Just need to work on color scheme, tab order, exception handling, and commets. Also Instructions*

commit 80bde8953b2f0f92f25039ac4c706b2a74bb3ed2
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Sun Nov 28 00:41:15 2010 -0100

    Made program functional. Working on GUI Square problem

commit 491b4c3abd570c50c31fbf185032c42c8b18174e
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Nov 26 22:56:44 2010 -0100

    worked on gui more

commit 706aad75a0ae7d40c75d2c3cd0d41adbe1171c5a
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Nov 26 22:27:11 2010 -0100

    worked on gui

commit 66df9fbbe95200b006576a721445335e4edc6eeb
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Nov 26 22:13:07 2010 -0100

    added gui form .barebones

commit 3c131e266664f31d194e022f920f0c8a6f6c8dbe
Author: Brandon Russell <rbrandon87@gmail.com>
Date:   Fri Nov 26 21:58:34 2010 -0100

    first commit

