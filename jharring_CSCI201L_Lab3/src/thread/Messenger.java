package thread;

import java.util.Random;

public class Messenger extends Thread {
	MessageQueue mq;
	public Messenger(MessageQueue mq) {
		this.mq = mq;
	}
	public void run() {
		for(int i=1; i<=20; i++) {
			
			// insert new message into queue
			mq.addMessage("Message " + i );
			System.out.println(Util.getCurrentTime() + " Messenger - insert \"Message " + i + "\"");
			
			// make thread sleep
			Random rand = new Random();
			int timer = rand.nextInt(1000);
			
			try {
				Thread.sleep(timer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
