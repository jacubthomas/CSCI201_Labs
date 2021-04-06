package jharring_CSCI201L_Lab5;

public class LinearSearch {
	private int[] arr;
	private int target;
	
	public LinearSearch(int[] arr, int target) {
		this.arr = arr;
		this.target = target;
	}
	
	public int find() {
		
		long before = System.currentTimeMillis();
		long after;
		for(int i=0; i<arr.length; i++) {
				if(arr[i] == target) {
					after = System.currentTimeMillis();
					System.out.println("Linear Search: element found, " + i + ", in " + (after - before) + " milliseconds.");
					return i;
				}
			}
		after = System.currentTimeMillis();
		System.out.println("Linear Search: element not in array. Total search time, " + (after - before) + " milliseconds.");
		return -1;
	}

}
