package edu.neu.coe.info6205.sort.linearithmic;

import java.io.IOException;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.*;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodTester;
import edu.neu.coe.info6205.util.StatPack;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MergeSortDriver {
    @BeforeClass
    public static void beforeClass() throws IOException {
        config = Config.load(MergeSortDriver.class);
    }

    @Test
    public void instrumentsTest() {
        System.out.println("--------------- MergeSort Instrumentation -----------------");

        Config config1 = config.copy("helper", "instrument", "true");
        Config config2 = config1.copy(MergeSort.MERGESORT, MergeSort.INSURANCE, "true");
        Config config3 = config2.copy(MergeSort.MERGESORT, MergeSort.NOCOPY, "true");

        for(int n = 10000; n <= 256000; n *= 2) {
            System.out.println("MergeSort instrument variables of " + n + "-sized array");

            final Helper<Integer> helper = HelperFactory.create("MergeSort", n, config3);
            Sort<Integer> s = new MergeSort<>(helper);
            s.init(n);
            int finalN = n;
            final Integer[] xs = helper.random(Integer.class, r -> r.nextInt(finalN));
            helper.preProcess(xs);
            Integer[] ys = s.sort(xs);
            helper.postProcess(ys);
            final PrivateMethodTester privateMethodTester = new PrivateMethodTester(helper);
            final StatPack statPack = (StatPack) privateMethodTester.invokePrivate("getStatPack");

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
        System.out.println("----------------- MergeSort Execution Time -----------------");

        Config config1 = config.copy(MergeSort.MERGESORT, MergeSort.INSURANCE, "true");
        Config config2 = config1.copy(MergeSort.MERGESORT, MergeSort.NOCOPY, "true");

        for(int n = 10000, m = 100; n <= 256000; n *= 2) {
            Benchmark_Timer<Integer> bm = new Benchmark_Timer<>(
                    "MergeSort",
                    null,
                    (Integer N) -> {
                        MergeSort<Integer> sorter = new MergeSort<>(N, config2);
                        Helper<Integer> helper = sorter.getHelper();
                        Integer[] ints = helper.random(Integer.class, r -> r.nextInt(N));
                        Integer[] sorted = sorter.sort(ints);
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

    private static Config config;
}
