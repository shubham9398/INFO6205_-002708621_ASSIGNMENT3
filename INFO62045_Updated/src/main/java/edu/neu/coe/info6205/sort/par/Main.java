package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        Random random = new Random();
        int[] array = new int[6000000];
        ArrayList<Long> timeList = new ArrayList<>();
        for(int i= ParSort.getThreadNum(); i<= 64; i*=2) {
        	ParSort.setThreadNum(i);
        	 System.out.println("ArraySize：" + (array.length) + "    No of Threads: " + ParSort.getThreadNum() );
        	 for (int j = 50; j < 100; j++) {
                 ParSort.cutoff = 10000 * (j + 1);
                 // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                 long time;
                 long startTime = System.currentTimeMillis();
                 for (int t = 0; t < 10; t++) {
                     for (int k = 0; k < array.length; k++) array[k] = random.nextInt(10000000);
                     ParSort.sort(array, 0, array.length);
                 }
                 long endTime = System.currentTimeMillis();
                 time = (endTime - startTime);
                 timeList.add(time);


                 System.out.println("cutoff：" + (ParSort.cutoff) + "\t\t10times Time:" + time + "ms");

             }
        	
        }
       
       
        try {
            FileOutputStream fis = new FileOutputStream("./src/result.csv");
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            bw.write("Cutoff"+","+"Cuttoff/ArraySize"+","+"thread = 1"+","+"thread = 2"+","+"thread = 4"+","+"thread = 8"+","+"thread = 16"+","+"thread = 32"+","+"thread = 64"+ "\n");
            int j = 50;
            for( int i=0;i<50;i++) {
              	String content = (double) 10000 * (j + 1) + "," +(double) 10000 * (j + 1)/4000000 +","+  (long)timeList.get(i) + "," +  (long)timeList.get(50+i)+ "," +  (long)timeList.get(100+i)+ "," +  (long)timeList.get(150+i)+ "," +  (long)timeList.get(200+i)+ "," +  (long)timeList.get(250+i)+ "," +  (long)timeList.get(300+i) + "\n";
                j++;
                bw.write(content);
                bw.flush();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
