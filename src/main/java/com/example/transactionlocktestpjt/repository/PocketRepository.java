package com.example.transactionlocktestpjt.repository;

import com.example.transactionlocktestpjt.domain.Pocket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PocketRepository extends JpaRepository<Pocket, Long> {

}
