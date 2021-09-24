package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    Optional<CreditCard> findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);
    Optional<CreditCard> findByAccountHolderId(Long accountHolderId);

}
