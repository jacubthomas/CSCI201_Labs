package jharring_CSCI201L_Lab5;

public class MultithreadedSearch extends Thread {
	private int[] arr;
	private int target;
	private int start;
	private int end;
	private long start_time;
	private long end_time;
	public static boolean found;
	public static int index = -1;

	public MultithreadedSearch(int[] arr, int target, int start, int end) {
		this.target = target;
		this.arr = arr;
		this.start = start;
		this.end = end;
		found = false;
	}

	 public void run() {
		start_time = System.currentTimeMillis();
		for(int i=start; i<end; i++) {
			if(arr[i] == target) {
				index = i;
				end_time = System.currentTimeMillis();
				found = true;
				System.out.println("Multithreaded Search: element found, " + i + ", in " + (end_time - start_time) + " milliseconds.");
				return;
			}
		}
		end_time = System.currentTimeMillis();
	}
	 public long getTime() {
		 return end_time - start_time;
	 }
}
