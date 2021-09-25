package com.ironhack.midtermproject.repository.LoginDataRepositories;

import com.ironhack.midtermproject.dao.LoginData.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
//    @Query(
//            value = "SELECT "
//    )
//    Optional<AccountHolder> findByUserId(Long userId);
    Optional<AccountHolder> findById(Long id);
}
