package com.example.transactionlocktestpjt.repository;

import com.example.transactionlocktestpjt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
