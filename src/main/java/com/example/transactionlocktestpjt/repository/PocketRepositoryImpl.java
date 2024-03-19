package com.example.transactionlocktestpjt.repository;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.domain.QPocket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public class PocketRepositoryImpl implements PocketQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public PocketRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Pocket> findByUserId(Long userId) {
        QPocket pocket = QPocket.pocket;
        return queryFactory.selectFrom(pocket)
            .where(pocket.user.userId.eq(userId))
            .fetch();
    }

    @Override
    public Optional<Pocket> findUserPocketWithPessimisticLock(Long pocketId) {
        QPocket pocket = QPocket.pocket;
        return Optional.ofNullable(queryFactory.selectFrom(pocket)
            .where(pocket.pocketId.eq(pocketId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchFirst());
    }

    @Override
    public Optional<Pocket> findUserPocketWithOptimisticLock(Long pocketId) {
        QPocket pocket = QPocket.pocket;
        return Optional.ofNullable(queryFactory.selectFrom(pocket)
            .where(pocket.pocketId.eq(pocketId))
            .setLockMode(LockModeType.OPTIMISTIC)
            .fetchFirst()
        );
    }
}
