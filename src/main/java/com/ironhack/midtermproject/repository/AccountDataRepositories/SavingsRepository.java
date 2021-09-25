package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.CreditCard;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
    Optional<Savings> findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    Optional<Savings> findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);
    Optional<Savings> findByAccountHolder(AccountHolder accountHolder);

}
