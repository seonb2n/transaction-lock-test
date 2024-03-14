package com.example.transactionlocktestpjt.service;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.domain.User;
import com.example.transactionlocktestpjt.repository.PocketRepository;
import com.example.transactionlocktestpjt.repository.UserRepository;
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

}