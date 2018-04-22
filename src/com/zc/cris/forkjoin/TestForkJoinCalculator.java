package com.zc.cris.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoinCalculator {

    // java8 之前使用forkjoin 的流程
    @Test
    public void test() {
        Instant start = Instant.now();      // java8 的时间 API

        // forkJoin 线程池
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculator(0L, 1000000000L);
        Long num = pool.invoke(task);
        System.out.println(num);
        Instant end = Instant.now();

        // java8 使用时间 API 得到两个时间差
        System.out.println("需要时间：" + Duration.between(start, end).toMillis());  // 2120

    }

    // 使用单线程执行
    @Test
    public void test1() {
        Instant start = Instant.now();      // java8 的时间 API
        Long sum = 0L;
        for (int i = 0; i < 1000000000L; i++) {
            sum += i;
        }
        System.out.println(sum);

        Instant end = Instant.now();
        // java8 使用时间 API 得到两个时间差
        System.out.println("需要时间：" + Duration.between(start, end).toMillis());  // 3027
    }

    // 使用 java8 提供的并行流进行数据处理（效率提升非常高。。。）
    @Test
    public void test3() {
        Instant start = Instant.now();      // java8 的时间 API

        LongStream.rangeClosed(0, 1000000000L)
                .parallel()         // 切换到并行流模式处理
                .reduce(0, Long::sum);

        Instant end = Instant.now();
        // java8 使用时间 API 得到两个时间差
        System.out.println("需要时间：" + Duration.between(start, end).toMillis());  // 192
    }
}
