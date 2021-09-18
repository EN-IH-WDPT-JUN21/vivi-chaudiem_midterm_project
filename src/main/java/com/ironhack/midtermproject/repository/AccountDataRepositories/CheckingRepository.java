package com.ironhack.midtermproject.repository.AccountDataRepositories;

import com.ironhack.midtermproject.dao.AccountData.Checking;

import javax.transaction.Transactional;

@Transactional
public interface CheckingRepository extends AccountRepository<Checking> {
}
