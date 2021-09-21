package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
