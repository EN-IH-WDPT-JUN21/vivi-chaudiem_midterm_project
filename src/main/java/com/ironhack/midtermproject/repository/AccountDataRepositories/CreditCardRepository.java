package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    CreditCard findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);
}
