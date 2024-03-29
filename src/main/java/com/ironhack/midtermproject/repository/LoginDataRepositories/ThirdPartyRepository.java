package com.ironhack.midtermproject.repository.LoginDataRepositories;

import com.ironhack.midtermproject.dao.LoginData.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    Optional<ThirdParty> findById(Long id);
}
