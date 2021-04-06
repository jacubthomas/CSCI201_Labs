package jharring_CSCI201L_Lab5;

import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Long> {
	private int[] arr;
	private int target;
	private int start;
	private int end;
	private int index;
	private long start_time;
	private long end_time;
	public static boolean found = false;
	public static final long serialVersionUID = 1;
	
	public ParallelSearch(int[] arr, int target, int start, int end) {
		this.target = target;
		this.arr = arr;
		this.start = start;
		this.end = end;
		this.index = -1;
	}

	@Override
	protected Long compute() {
		start_time = System.currentTimeMillis();
		for(int i=start; i<=end; i++) {
			if(arr[i] == target) {
				index = i;
				end_time = System.currentTimeMillis();
				ParallelSearch.found = true;
				System.out.println("Parallel Search: element found, " + i + ", in " + (end_time - start_time) + " milliseconds.");
				return end_time - start_time;
			}
		}
		end_time = System.currentTimeMillis();
		return end_time - start_time;
	}
}
