package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
    Checking findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    Checking findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);
}
