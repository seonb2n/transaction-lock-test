package com.example.transactionlocktestpjt.repository;

import com.example.transactionlocktestpjt.domain.Pocket;
import com.example.transactionlocktestpjt.domain.QPocket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;

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
}
