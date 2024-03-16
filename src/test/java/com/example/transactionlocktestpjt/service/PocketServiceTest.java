package com.example.transactionlocktestpjt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.domain.User;
import com.example.transactionlocktestpjt.repository.PocketRepository;
import com.example.transactionlocktestpjt.repository.UserRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PocketServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PocketRepository pocketRepository;

    @Autowired
    private PocketService pocketService;

    Logger logger = LoggerFactory.getLogger(PocketServiceTest.class);

    private User user;
    private Pocket pocket;

    @BeforeEach
    void setUp() throws Exception {
        user = User.builder().userId(1L).build();
        userRepository.save(user);
        pocket = Pocket.builder().pocketId(1L).user(user).point(100L).build();
        pocketRepository.save(pocket);
        user.getPocketList().add(pocket);
    }

    @DisplayName("QueryDSL 을 사용해서 사용자의 모든 잔액을 가져온다.")
    @Test
    void testFindTotalPocketPointByUserId() {
        var point = pocketService.findTotalPocketPointByUserId(1L);
        logger.info("point: {}", point);
    }

    @DisplayName("트랜잭션을 사용하지 않는 경우, 값이 덮어 씌워질 것이다.")
    @Test
    void testAddPointWithoutTransaction() throws InterruptedException {
        final int threadNumber = 10;
        final long inputPoint = 100L;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            executorService.execute(() -> {
                pocketService.addPointWithoutTransaction(1L, inputPoint);
                var point = pocketService.findTotalPocketPointByUserId(1L);
                logger.info("point: {}", point);
                latch.countDown();
            });
        }
        latch.await();
        var point = pocketService.findTotalPocketPointByUserId(1L);
        // mysql 의 default transaction isolation level 은 repeatable read
        assertEquals(200, point);
    }

    @DisplayName("repeatable read 트랜잭션을 사용하는 경우에는 값이 덮어 씌워질 것이다.")
    @Test
    void testAddPointWithDefaultTransaction() throws InterruptedException {
        final int threadNumber = 10;
        final long inputPoint = 100L;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            executorService.execute(() -> {
                pocketService.addPointWithRepeatableReadTransaction(1L, inputPoint);
                var point = pocketService.findTotalPocketPointByUserId(1L);
                logger.info("point: {}", point);
                latch.countDown();
            });
        }
        latch.await();
        var point = pocketService.findTotalPocketPointByUserId(1L);
        // mysql 의 default transaction isolation level 은 repeatable read
        assertEquals(200, point);
    }

    @DisplayName("트랜잭션 Isolation 이 Serializable 인 경우, DeadLock 이 발생한다.")
    @Test
    void testAddPointWithSerializeTransaction() throws InterruptedException {
        final int threadNumber = 10;
        final long inputPoint = 100L;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            executorService.execute(() -> {
                pocketService.addPointWithSerializeTransaction(1L, inputPoint);
                var point = pocketService.findTotalPocketPointByUserId(1L);
                logger.info("point: {}", point);
                latch.countDown();
            });
        }
        latch.await();
    }
}
