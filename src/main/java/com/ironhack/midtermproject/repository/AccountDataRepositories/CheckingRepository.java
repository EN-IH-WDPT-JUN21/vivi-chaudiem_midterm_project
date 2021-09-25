package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
    Optional<Checking> findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    Optional<Checking> findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);
    Optional<Checking> findByAccountHolder(AccountHolder accountHolder);
}
