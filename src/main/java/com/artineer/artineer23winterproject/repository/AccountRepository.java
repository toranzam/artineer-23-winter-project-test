package com.artineer.artineer23winterproject.repository;

import com.artineer.artineer23winterproject.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
