package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Owner;
import com.ironhack.midtermproject.dao.AccountData.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
    Savings findByIdAndPrimaryOwner(Long id, Owner primaryOwner);
    Savings findByIdAndSecondaryOwner(Long id, Owner secondaryOwner);

}
