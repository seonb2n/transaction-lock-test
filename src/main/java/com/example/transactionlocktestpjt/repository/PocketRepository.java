package com.example.transactionlocktestpjt.repository;

import com.example.transactionlocktestpjt.domain.Pocket;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<Pocket, Long>, PocketQueryDslRepository {

}

interface PocketQueryDslRepository{
    List<Pocket> findByUserId(Long userId);

    Optional<Pocket> findUserPocketWithPessimisticLock(Long pocketId);
}
