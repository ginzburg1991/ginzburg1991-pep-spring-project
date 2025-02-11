package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


    public Account register(Account account){
        if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("Username cannot be blank");
        }
        if(account.getPassword() == null || account.getPassword().trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be blank");
        }
        if(account.getPassword() == null || account.getPassword().length() < 4){
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if(existing.isPresent()){
            throw new IllegalStateException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Account login(Account account){
        Optional<Account> existing = accountRepository.findByUsername(account.getUsername());
        if(existing.isPresent() && existing.get().getPassword().equals((account.getPassword()))) {
            return existing.get();
        } else{
            throw new SecurityException("Invalid username or password");
        }
    }
}
