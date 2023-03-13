package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.*;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodTester;
import edu.neu.coe.info6205.util.StatPack;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuickSortDualPivotDriver {
    @BeforeClass
    public static void beforeClass() throws IOException {
        config = Config.load();
    }

    @Test
    public void timeTest() {
        System.out.println("----------------- QuickSort Execution Time -----------------");

        for(int n = 10000, m = 100; n <= 256000; n *= 2) {
            Benchmark_Timer<Integer> bm = new Benchmark_Timer<>(
                    "QuickSort Dual Pivots",
                    null,
                    (Integer N) -> {
                        SortWithHelper<Integer> sorter = new QuickSort_DualPivot<>(N, config);
                        Helper<Integer> helper = sorter.getHelper();
                        Integer[] arr = helper.random(Integer.class, r -> r.nextInt(N));
                        Integer[] sorted = sorter.sort(arr);
                        // "helper.sorted(array)" used to check the array is ordered or not;
                        assertTrue(helper.sorted(sorted));
                    },
                    null
            );

            double x = bm.run(n, m);
            System.out.println("Array size: " + n + " - " + x + "ms.");

            if(n == 160000) n = 128000;
        }

        System.out.println();
    }

    @Test
    public void instrumentsTest() {
        System.out.println("--------------- QuickSort Dual Pivots Instrument Variables -----------------");

        Config config1 = Config.setupConfig("true", "0", "1", "", "");

        for(int n = 10000; n <= 256000; n *= 2) {
            System.out.println("QuickSort Dual Pivots instrument variables of " + n + "-sized array");

            final BaseHelper<Integer> helper = (BaseHelper<Integer>) HelperFactory.create("QuickSort Dual Pivots", n, config1);
            System.out.println(helper);
            Sort<Integer> s = new QuickSort_DualPivot<>(helper);
            s.init(n);
            int finalN = n;
            final Integer[] arr = helper.random(Integer.class, r -> r.nextInt(finalN));
            helper.preProcess(arr);
            Integer[] ys = s.sort(arr);
            assertTrue(helper.sorted(ys));
            helper.postProcess(ys);

            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");

            final int compares = (int) statPack.getStatistics(InstrumentedHelper.COMPARES).mean();
            final int inversions = (int) statPack.getStatistics(InstrumentedHelper.INVERSIONS).mean();
            final int fixes = (int) statPack.getStatistics(InstrumentedHelper.FIXES).mean();
            final int swaps = (int) statPack.getStatistics(InstrumentedHelper.SWAPS).mean();
            final int copies = (int) statPack.getStatistics(InstrumentedHelper.COPIES).mean();

            System.out.println("Compares: " + compares);
            System.out.println("Inversions : " + inversions);
            System.out.println("Fixes: " + fixes);
            System.out.println("Swaps: " + swaps);
            System.out.println("Copies: " + copies);
            System.out.println("---------------------------------------------------------------");

            if(n == 160000) n = 128000;
        }
    }

    private static Integer[] arrayCreate(int n) {
        Random rd = new Random();
        Integer[] res = new Integer[n];
        for(int i = 0; i < n; i++) {
            res[i] = rd.nextInt(n);
        }
        return res;
    }

    private static Config config;
}
