package com.dev.spfcbank.service;

import com.dev.spfcbank.domain.user.User;
import com.dev.spfcbank.domain.user.UserType;
import com.dev.spfcbank.repository.UserRepository;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Transaction createTransaction(User sender, User receiver, BigDecimal amount) throws Exception {

        this.validateTransaction(sender, amount);


        return null;
    }

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType().equals(UserType.MERCHANT)){
            throw new Exception("User cannot perform transactions");
        }
        if (sender.getBalance().compareTo(amount) > 0){
            throw new Exception("Insufficient balance");
        }
    }
}
