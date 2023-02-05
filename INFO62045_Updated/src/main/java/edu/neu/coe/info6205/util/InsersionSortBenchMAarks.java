package edu.neu.coe.info6205.util;


import edu.neu.coe.info6205.sort.elementary.InsertionSort;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class InsersionSortBenchMAarks {
	
	
	
	
	
	public static void main(String[] args) {
		
		 Random r = new Random();
	        int m = 500;   // number of runs
	        int a = 1000; // length for randomly ordered array
	        int b = 1000; // length for ordered array
	        int c = 1000; // length for reverse ordered array
	        int d = 1000; // length for partially ordered array

	        /**
	         * Timing the randomly ordered array
	         * for e.g : {1,3,2,1,7,2,....N}
	         * returns time for randomly ordered array
	         */
	        
	        for (int k = 0; k < 7; k++) {
	            a = a * 2;
	            InsertionSort<Integer> iSort = new InsertionSort<>();
	            Consumer<Integer[]> consumer = array -> iSort.sort(array, 0, array.length);
	            Benchmark_Timer<Integer[]> benchTimer = new Benchmark_Timer<>("Insertion sort for randomArray with array length : " + a, consumer);
	            int N = a;
	            Supplier<Integer[]> randomSupplier = () -> {
	                Integer[] array = new Integer[N];

	                for (int i = 0; i < N; i++)
	                    array[i] = r.nextInt();
	                return array;
	            };
	            consumer.accept(randomSupplier.get());
	            System.out.println(benchTimer.run(randomSupplier.get(), m));
	        }
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

	        /**
	         * Timing the ordered array
	         * for e.g : {1,2,3,....N}
	         * returns time for ordered array
	         */
	        for (int k = 0; k < 7; k++) {
	            b = b * 2;
	            InsertionSort<Integer> iSort = new InsertionSort<>();
	            Consumer<Integer[]> consumer = array -> iSort.sort(array, 0, array.length);
	            Benchmark_Timer<Integer[]> benchTimer = new Benchmark_Timer<>("Insertion sort for orderedArray with array length : " + b, consumer);
	            int N = b;

	            Supplier<Integer[]> orderedSupplier = () -> {
	                Integer[] array = new Integer[N];

	                for (int i = 0; i < N; i++)
	                    array[i] = i;
	                return array;
	            };
	            consumer.accept(orderedSupplier.get());
	            System.out.println(benchTimer.run(orderedSupplier.get(), m));
	        }
	        /**
	         * Timing the reverse array
	         * for e.g : {N,N-1,N-2,......,1}
	         * returns time for reverse array
	         */
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
	        for (int k = 0; k < 7; k++) {
	            c = c * 2;
	            InsertionSort<Integer> iSort = new InsertionSort<>();
	            Consumer<Integer[]> consumer = array -> iSort.sort(array, 0, array.length);
	            Benchmark_Timer<Integer[]> benchTimer = new Benchmark_Timer<>("Insertion sort for reverseArray with array length : " + c, consumer);
	            int N = c;

	            Supplier<Integer[]> reverseSupplier = () -> {
	                Integer[] array = new Integer[N];

	                for (int i = N - 1; i >= 0; i--)
	                    array[i] = i;
	                return array;
	            };
	            consumer.accept(reverseSupplier.get());
	            System.out.println(benchTimer.run(reverseSupplier.get(), m));
	        }
	        /**
	         * Timing the partially ordered array
	         * for e.g : {1,2,3,N/2,1,2,3....N}
	         * returns time for partially ordered array
	         */
	        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
	        for (int k = 0; k < 7; k++) {
	            d = d * 2;
	            InsertionSort<Integer> iSort = new InsertionSort<>();
	            Consumer<Integer[]> consumer = array -> iSort.sort(array, 0, array.length);
	            Benchmark_Timer<Integer[]> benchTimer = new Benchmark_Timer<>("Insertion sort for partialArray with array length : " + d, consumer);
	            int N = d;

	            Supplier<Integer[]> partialOrderedSupplier = () -> {
	                Integer[] array = new Integer[N];

	                for (int i = 0; i < N / 2; i++)
	                    array[i] = i;
	                for (int j = N / 2; j < N; j++)
	                    array[j] = r.nextInt();
	                return array;
	            };
	            consumer.accept(partialOrderedSupplier.get());
	            System.out.println(benchTimer.run(partialOrderedSupplier.get(), m));
	        }
	    }
	
	    }