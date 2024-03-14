package com.example.transactionlocktestpjt.service;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.repository.PocketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PocketService {

    private final PocketRepository pocketRepository;

    public PocketService(PocketRepository pocketRepository) {
        this.pocketRepository = pocketRepository;
    }

    @Transactional(readOnly = true)
    public Long findTotalPocketPointByUserId(Long userId) {
        return pocketRepository.findByUserId(userId).stream().mapToLong(Pocket::getPoint).sum();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addPointWithSerializeTransaction(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow();
        pocket.addPoint(point);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addPointWithRepeatableReadTransaction(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow();
        pocket.addPoint(point);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addPointWithReadCommittedTransaction(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow();
        pocket.addPoint(point);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void addPointWithReadUncommittedTransaction(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow();
        pocket.addPoint(point);
    }
}
