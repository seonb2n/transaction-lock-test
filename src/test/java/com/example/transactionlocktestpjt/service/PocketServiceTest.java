package com.example.transactionlocktestpjt.service;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.domain.User;
import com.example.transactionlocktestpjt.repository.PocketRepository;
import com.example.transactionlocktestpjt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private User user;
    private Pocket pocket;

    @BeforeEach
    void setUp() throws Exception {
        user = User.builder().userId(1L).build();
        pocket = Pocket.builder().pocketId(1L).user(user).point(0L).build();
        user.getPocketList().add(pocket);
        userRepository.save(user);
        pocketRepository.save(pocket);
    }

    @Test
    void testFindTotalPocketPointByUserId() {
        var point = pocketService.findTotalPocketPointByUserId(1L);
    }

}