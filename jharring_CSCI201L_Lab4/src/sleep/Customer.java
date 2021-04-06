package sleep;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer extends Thread {

	private int customerName;
	private SleepingBarber sb1;
	private SleepingBarber sb2;
	private Lock customerLock;
	private Condition gettingHaircutCondition;
	public Customer(int customerName, SleepingBarber sb1, SleepingBarber sb2) {
		this.customerName = customerName;
		this.sb1 = sb1;
		this.sb2 = sb2;
		customerLock = new ReentrantLock();
		gettingHaircutCondition = customerLock.newCondition();
	}

	public int getCustomerName() {
		return customerName;
	}
	
	public void startingHaircut() {
		Util.printMessage("Customer " + customerName + " is getting hair cut.");
	}
	
	public void finishingHaircut() {
		Util.printMessage("Customer " + customerName + " is done getting hair cut.");
		try {
			// acquires the lock
			customerLock.lock();
			
			// signals customer haircut is finished, and to release the customer lock
			gettingHaircutCondition.signal();
			
		} finally {
			// assures the lock is released
			customerLock.unlock();
		}
	}
	public void run() {
		boolean seatsAvailable = SleepingBarber.addCustomerToWaiting(this);
		if (!seatsAvailable) {
			Util.printMessage("Customer " + customerName + " leaving...no seats available.");
			return;
		}
		if(sb1.isSleeping) {
			sb1.wakeUpBarber();
		} else  if(sb2.isSleeping){
			sb2.wakeUpBarber();
		}
		try {
			customerLock.lock();
			gettingHaircutCondition.await();
		} catch (InterruptedException ie) {
			System.out.println("ie getting haircut: " + ie.getMessage());
		} finally {
			customerLock.unlock();
		}
		Util.printMessage("Customer " + customerName + " is leaving.");
	}
}
