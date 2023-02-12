package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.Scanner;

public class HWQUPC_Solution {
	
	 public static int countPairs(int n) {
		 int connections = 0;
	        UF_HWQUPC u = new UF_HWQUPC(n);
	        Random r = new Random();
	        while (u.components() != 1) {
	            connections++;
	            int i = r.nextInt(n);
	            int j = r.nextInt(n);
	            if (!u.connected(i, j)) {
	                u.union(i, j);
	            }
	        }
	        return connections;
	    }


	    public static void main(String[] args) {
	    	int count=0;
	    	while(count<20) {
	    		Random r = new Random();
	    		int n = r.nextInt(10000);
	    		int sum = 0;
	            for(int i = 0;i<1000;i++) {
	                sum = sum + countPairs(n);
	            }
	            System.out.println("The number of nodes (n) : " + n +" and Connections (m) "+ sum/1000);
	            count++;
	    		
	    	}
	    	
	    }
	    
}

