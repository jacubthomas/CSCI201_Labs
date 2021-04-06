package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageTest {

	public MessageTest() {}
	
	public static void main(String [] args) {
		
		for(int i=0; i<2; i++) {
			ExecutorService executor = Executors.newFixedThreadPool(2);
			MessageQueue mq = new MessageQueue();
			
			// add threads to pool
			executor.execute(new Messenger(mq));
			executor.execute(new Subscriber(mq));
			
			// terminates the pool upon completion of threads run()
			executor.shutdown();
			
			while(!executor.isTerminated()) {
				// prevents the main thread from proceeding until current pool has been terminated
				Thread.yield();
			}
		}
	}
}





/* 
 * 1) the MessageQueue class is a FIFO structure implemented with an ArrayList, where messages are added
 * 	  to the back and retrieved from the front.
 * 
 * 2) A Queue or Linked-List with head tail pointers
 * 
 * 3) Yes, we instantiate a thread when we create and pass the new Messenger object to the Executor.
 * 
 * 4) Code is implemented within messenger's run() method. The run() method is inherited from the thread
 * 	  class which implements a runnableInterface and thus requires a method implementation as it is not 
 * 	  provided by the interface.
 * 
 * 5) The print statements are made unique to differentiate between the the concurrent thread executions.
 * 
 * 6) The messenger class will iterate through 20 insertions and exit, whereas the subscriber class will
 * 	  will query the MessageQueue > 20 times and exit only after retrieving 20 messages successfully.
 * 
 * 7) You will likely not retrieve all the messages from the queue.
 * 
 * 8) The Subscriber class takes a parameter a MessageQueue object that it shares with Messenger. This is
 *    necessary as the MQ object acts as the storage container for the data these threads operate on.
 *    
 * 9) Using the thread yield() inside main, we ensure the threads within executor are able to complete their
 *    executions prior to the main thread is killed.
 *    
 * 10) We execute the threads using a threadpool handler, and use the yield() paired with a thread termination
 *     check to ensure the threads run to completion before moving on.
 *     
 * 11) We use the isTerminated() provided by the Executor object to check the status of the threads in
 *     the pool.
 * 12) No, we use the executors execute() because we are handling the threads using a threadpool vice
 * 	   individually.
 */

