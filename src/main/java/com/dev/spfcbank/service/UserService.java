package com.dev.spfcbank.service;

import com.dev.spfcbank.domain.user.User;
import com.dev.spfcbank.domain.user.UserDTO;
import com.dev.spfcbank.domain.user.UserType;
import com.dev.spfcbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType().equals(UserType.MERCHANT)){
            throw new Exception("User cannot perform transactions");
        }
        if (sender.getBalance().compareTo(amount) > 0){
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(UUID id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public List<User> findAllUsers(){
        return this.repository.findAll();
    }

    public User createUser(UserDTO data){
        return saveUser(new User(data));
    }

    public User saveUser(User user){
        return this.repository.save(user);
    }
}
