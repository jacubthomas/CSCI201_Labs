package sleep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SleepingBarber extends Thread {

	private static int maxSeats = 3;
	private static int totalCustomers = 10;
	private static ArrayList<Customer> customers = new ArrayList<Customer>();
	// thread-safe implementation
	private static List<Customer> customersWaiting =
								Collections.synchronizedList(customers);
	private static Lock barberLock = new ReentrantLock(true);
	private static Condition sleepingCondition = barberLock.newCondition();
	private static boolean moreCustomers = true;
	private String name;
	public boolean isSleeping;
	
	public SleepingBarber(String name) {
		this.name = name;
		isSleeping = true;
		this.start();
	}
	
	
	// method declared static as it interacts with a static List customersWaiting
	public static synchronized boolean addCustomerToWaiting(Customer customer) {
		if (customersWaiting.size() == maxSeats) {
			return false;
		}
		Util.printMessage("Customer " + customer.getCustomerName() + " is waiting");
		customersWaiting.add(customer);
		String customersString = "";
		for (int i=0; i < customersWaiting.size(); i++) {
			customersString += customersWaiting.get(i).getCustomerName();
			if (i < customersWaiting.size() - 1) {
				customersString += ",";
			}
		}
		Util.printMessage("Customers currently waiting: " + customersString);
		return true;
	}
	
	public void wakeUpBarber() {
		try {
			// acquires the lock
			barberLock.lock();
			
			// update barber isSleeping
			this.isSleeping = true;
			
			// wakes thread with lock
			sleepingCondition.signal();
			
		} finally {
			
			// guarantees release of lock
			barberLock.unlock();
		}
	}
	
	public void run() {
		while(moreCustomers) {
			while(!customersWaiting.isEmpty()) {
				Customer customer = null;
				
				// ASK ABOUT THIS! --------------------------------------------------
				synchronized(this) {
					customer = customersWaiting.remove(0);
				} //-----------------------------------------------------------------
				customer.startingHaircut();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					System.out.println("ie cutting customer's hair" + ie.getMessage());
				}
				customer.finishingHaircut();
				Util.printMessage(this.name + " is checking for more customers...");		
			}
			
			try {
				// acquires the lock to use await condition
				barberLock.lock();
				Util.printMessage("No customers, so time to sleep (" + this.name + ")...");
				
				// updates status to sleeping
				this.isSleeping = true;
				sleepingCondition.await();
				
				// condition been signaled by waiting customer
				Util.printMessage("Someone woke " + this.name + " up!");
			} catch (InterruptedException ie) {
				System.out.println("ie while sleeping: " + ie.getMessage());
			} finally {
				barberLock.unlock();
			}
		}
		Util.printMessage("All done for today! " + this.name + ", time to go home!");
	}
	
	public static void main(String [] args) {
		SleepingBarber sb1 = new SleepingBarber("John");
		SleepingBarber sb2 = new SleepingBarber("Jacob");
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i=0; i < totalCustomers; i++) {
			Customer customer = new Customer(i, sb1, sb2);
			executors.execute(customer);
			try {
				Random rand = new Random();
				int timeBetweenCustomers = rand.nextInt(2000);
				Thread.sleep(timeBetweenCustomers);
			} catch (InterruptedException ie) {
				System.out.println("ie in customers entering: " + ie.getMessage());
			}
		}
		executors.shutdown();
		while(!executors.isTerminated()) {
			Thread.yield();
		}
		Util.printMessage("No more customers coming today...");
		moreCustomers = false;
		sb1.wakeUpBarber();
		sb2.wakeUpBarber();
	}
}
