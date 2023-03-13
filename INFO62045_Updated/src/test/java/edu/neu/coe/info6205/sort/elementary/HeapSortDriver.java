package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodTester;
import edu.neu.coe.info6205.util.StatPack;
import org.junit.Test;

import java.util.Random;
import static org.junit.Assert.assertTrue;

public class HeapSortDriver {
    @Test
    public void instrumentsTest() {
        System.out.println("--------------- HeapSort Instrument Variables -----------------");

        final Config config = Config.setupConfig("true", "0", "1", "", "");

        for(int n = 10000; n <= 256000; n *= 2) {
            System.out.println("HeapSort instrument variables of " + n + "-sized array");

            Helper<Integer> helper = HelperFactory.create("HeapSort", n, config);
            helper.init(n);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");
            Integer[] xs = arrayCreate(n);
            SortWithHelper<Integer> sorter = new HeapSort<Integer>(helper);
            sorter.preProcess(xs);
            Integer[] ys = sorter.sort(xs);
            sorter.postProcess(ys);
           

            
           


            final int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
            final int inversions = (int) statPack.getStatistics(InstrumentedHelper.INVERSIONS).mean();
            final int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
            final int swaps = (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean();
            final int copies = (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean();
            final int hits = (int) statPack.getStatistics(InstrumentedHelper.HITS).mean();

            System.out.println("Compares: " + compares);
            System.out.println("Inversions : " + inversions);
            System.out.println("Fixes: " + fixes);
            System.out.println("Swaps: " + swaps);
            System.out.println("Copies: " + copies);
            System.out.println("Hits: " + hits);
            System.out.println("---------------------------------------------------------------");

            if(n == 160000) n = 128000;
        }
    }

    @Test
    public void timeTest() {
        System.out.println("----------------- HeapSort Execution Time -----------------");

        Config config = Config.setupConfig("false", "", "0", "", "");

        for(int n = 10000, m = 100; n <= 256000; n *= 2) {
            Integer[] a = arrayCreate(n);

            Benchmark_Timer<Integer[]> bm = new Benchmark_Timer<>(
                    "HeapSort",
                    null,
                    (Integer[] arr) -> {
                        BaseHelper<Integer> helper = new BaseHelper<>("HeapSort", arr.length, config);
                        GenericSort<Integer> sorter = new HeapSort<Integer>(helper);
                        Integer[] ys = sorter.sort(arr);
                        assertTrue(helper.sorted(ys));
                    },
                    null
            );

            double x = bm.run(a, m);
            System.out.println("Array size: " + n + " - " + x + "ms.");

            if(n == 160000) n = 128000;
        }

        System.out.println();
    }

    private static Integer[] arrayCreate(int n) {
        Random rd = new Random();
        Integer[] res = new Integer[n];
        for(int i = 0; i < n; i++) {
            res[i] = rd.nextInt(n);
        }
        return res;
    }
}
