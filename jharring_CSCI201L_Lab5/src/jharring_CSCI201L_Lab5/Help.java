package jharring_CSCI201L_Lab5;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Help {

	public static void main(String[] args) {
		Random randy = new Random();
		int rand_int = randy.nextInt(100000000 + 1);
		int false_int = 100000256;
		int[] arr = new int[100000000];
		
		for(int i=0; i<100000000; i++) {
			arr[i] = i+1;	
		}
		
		LinearSearch ls = new LinearSearch(arr, rand_int);
//		LinearSearch ls = new LinearSearch(arr, false_int);
		ls.find();
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		MultithreadedSearch mts[] = new MultithreadedSearch[4];
		for(int i=0; i<4; i++) {
			int start = i * (arr.length/4);
			int end = (arr.length/4) * (i+1);
			if(i == 3) {
				end += arr.length%4;
			}
			mts[i] = new MultithreadedSearch(arr, rand_int, start, end);
//			mts[i] = new MultithreadedSearch(arr, false_int, start, end);
		}
		
		
		executor.execute(mts[0]);
		executor.execute(mts[1]);
		executor.execute(mts[2]);
		executor.execute(mts[3]);
		
		executor.shutdown();
		
		while(!executor.isTerminated()) {
			Thread.yield();
		}
		
		long time = 0;
		if(!MultithreadedSearch.found) {
			time = mts[0].getTime();
			time += mts[1].getTime();
			time += mts[2].getTime();
			time += mts[3].getTime();
			System.out.println("Multithreaded Search: element not in array. Total search time, " +
					time + " milliseconds");
		}
		
		ForkJoinPool pool = new ForkJoinPool(4);
		ParallelSearch[] ps = new ParallelSearch[4];
		for(int i=0; i<4; i++) {
			int start = i * (arr.length/4);
			int end = (arr.length/4) * (i+1);
			if(i == 3) {
				end += arr.length%4 -1 ;
			}
			ps[i] = new ParallelSearch(arr, rand_int, start, end);
//			ps[i] = new ParallelSearch(arr, false_int, start, end);
			pool.invoke(ps[i]);
		}
		
		if(!ParallelSearch.found) {
			time = 0;
			for(int i=0; i<4; i++) {
				time += ps[i].join();
			}
			System.out.println("Parallel Search: element not in array. Total search time, " +
					time + " milliseconds");
		}
	}
		
}

