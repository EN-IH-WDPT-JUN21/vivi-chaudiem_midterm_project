package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
