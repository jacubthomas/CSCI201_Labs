package thread;

import java.util.Random;

public class Subscriber extends Thread {
	MessageQueue mq;
	public Subscriber(MessageQueue mq) {
		this.mq = mq;
	}
	public void run() {
		int count = 0;
		Random rand = new Random();
		int timer = 0;
		while(count < 20) {
			// attempt to retrieve 
			String message = mq.getMessage();
			
			// check if new or null
			if(message.isEmpty()) {
				System.out.println(Util.getCurrentTime() + " Subscriber – tried to read but no message...");
			} else { 
				System.out.println(Util.getCurrentTime() + " Subscriber - read \"" + message  + "\" ");
				count++; 
				}
			
			// make thread sleep
			timer = rand.nextInt(1000);
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
