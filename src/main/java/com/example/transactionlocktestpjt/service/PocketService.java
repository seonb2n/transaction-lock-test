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

    public void addPointWithoutTransaction(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow();
        pocket.addPoint(point);
        pocketRepository.save(pocket);
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

    /**
     * 비관락을 사용해서 포인트를 업데이트한다.
     * @param pocketId
     * @param point
     * @return
     */
    @Transactional
    public void addPointWithPessimisticLock(Long pocketId, Long point) {
        Pocket pocket =  pocketRepository.findUserPocketWithPessimisticLock(pocketId).orElseThrow();
        pocket.addPoint(point);
    }

    public void addPointWithOptimisticLock(Long pocketId, Long point) {
        Pocket pocket = pocketRepository.findUserPocketWithOptimisticLock(pocketId).orElseThrow();
        pocket.addPoint(point);
    }
}
