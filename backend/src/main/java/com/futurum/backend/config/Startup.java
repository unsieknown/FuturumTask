package com.futurum.backend.config;

import com.futurum.backend.model.EmeraldAccount;
import com.futurum.backend.repositories.EmeraldAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class Startup {

    @Value("${init.emeraldAccount.initBalance}")
    private String initAccountBalance;

    private final EmeraldAccountRepository emeraldAccountRepository;

    @Bean
    public CommandLineRunner start() {
        return args -> {
            EmeraldAccount account = emeraldAccountRepository.findById(1)
                    .orElseGet(() -> {
                        BigDecimal balance = new BigDecimal(initAccountBalance);
                        EmeraldAccount acc = new EmeraldAccount();
                        acc.setBalance(balance);
                        return emeraldAccountRepository.save(acc);
                    });
        };
    }
}
