package com.irostub.telegramtapbot.repository;

import com.irostub.telegramtapbot.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
