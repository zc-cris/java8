package com.zc.cris.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * java 之前使用forkJoin 框架完成并行流操作
 */
public class ForkJoinCalculator extends RecursiveTask<Long> {

    public static final Long THREADHOLD = 100000L;
    private Long start;
    private Long end;

    public ForkJoinCalculator(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        Long length = end - start;
        Long sum = 0L;
        if (length <= THREADHOLD) {
            for (int i = 0; i < length; i++) {
                sum += i;
            }
            return sum;
        } else {
            Long mid = (start + end) / 2;
            // 拆分子任务，同时压入线程队列
            ForkJoinCalculator left = new ForkJoinCalculator(start, mid);
            left.fork();
            ForkJoinCalculator right = new ForkJoinCalculator(mid + 1, end);
            right.fork();

            // 合并子任务的结果
            return left.join() + right.join();
        }
    }
}
