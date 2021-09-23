package com.ironhack.midtermproject.repository;

import com.ironhack.midtermproject.dao.Transaction;
import com.ironhack.midtermproject.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOneIdAndAccountOneType(Long accountOneId, AccountType accountOneType);
}
