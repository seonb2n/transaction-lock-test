package com.example.etc;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest {

    private Future<String> asyncDoSomething(String jobName) {
        CompletableFuture<String> futureString = new CompletableFuture<String>();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            futureString.complete("Done " + jobName + LocalDateTime.now());
            System.out.println(jobName + " 처리");
        }).start();
        return futureString;
    }

    @Test
    void testCompletableFuture() {
        long start = System.nanoTime();
        Future<String> doSomethingFuture1 = asyncDoSomething("job 1");
        Future<String> doSomethingFuture2 = asyncDoSomething("job 2");

        try {
            String result1 = doSomethingFuture1.get();
            long job1CompletedTime1 = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(result1 + " 완료 (" + job1CompletedTime1 + "msecs)");

            String result2 = doSomethingFuture2.get();
            long job1CompletedTime2 = ((System.nanoTime() - start) / 1_000_000);
            System.out.println(result2 + " 완료 (" + job1CompletedTime2 + "msecs)");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
