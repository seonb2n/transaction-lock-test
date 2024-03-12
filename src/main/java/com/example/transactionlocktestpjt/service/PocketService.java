package com.example.transactionlocktestpjt.service;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.repository.PocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PocketService {

    private final PocketRepository pocketRepository;

    @Transactional(readOnly = true)
    public Long findTotalPocketPointByUserId(Long userId) {
        return pocketRepository.findByUserId(userId).stream().mapToLong(Pocket::getPoint).sum();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializeTransaction() {

    }


}
