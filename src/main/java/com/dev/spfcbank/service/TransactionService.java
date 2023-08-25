package com.dev.spfcbank.service;

import com.dev.spfcbank.domain.transaction.Transaction;
import com.dev.spfcbank.domain.transaction.TransactionDTO;
import com.dev.spfcbank.domain.user.User;
import com.dev.spfcbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${authorization.api.url}")
    private String authotizationApiUrl;

    public Transaction createTransaction(TransactionDTO data) throws Exception {
        User sender = userService.findUserById(data.sender());
        User receiver = userService.findUserById(data.receiver());

        userService.validateTransaction(sender, data.value());
        this.isAuthorized();
        return this.saveTransaction(sender, receiver, data.value());
    }

    public void isAuthorized() throws Exception {
        try {
            ResponseEntity<Map> response = this.restTemplate.getForEntity(authotizationApiUrl, Map.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("Connection failed with status:" + response.getStatusCode());
            }
            Map<String, String> body = response.getBody();
            if (body == null) {
                throw new Exception("Empty response body");
            }
            String message = body.get("message");
            if(!"Autorizado".equals(message)) {
                throw new Exception("Declined transaction");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception("Error while communicating with authorization server", e);
        } catch (RestClientException e) {
            throw new Exception("Connection error", e);
        }
    }

    public Transaction saveTransaction(User sender, User receiver, BigDecimal value) throws Exception {
        Transaction transaction = new Transaction(sender, receiver, value);
        repository.save(transaction);
        this.updateUserBalance(sender, receiver, value);
        return transaction;
    }

    public void updateUserBalance(User sender, User receiver, BigDecimal value){
        sender.setBalance(sender.getBalance().subtract(value));
        userService.saveUser(sender);
        receiver.setBalance(receiver.getBalance().add(value));
        userService.saveUser(receiver);
    }

    public Transaction findTransactionById(UUID id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }
}
