package com.futurum.backend.repositories;

import com.futurum.backend.model.EmeraldAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmeraldAccountRepository extends JpaRepository<EmeraldAccount, Integer> {
}
