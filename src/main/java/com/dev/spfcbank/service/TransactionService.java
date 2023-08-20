package com.dev.spfcbank.service;

import com.dev.spfcbank.domain.transaction.Transaction;
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

    public Transaction createTransaction(User sender, User receiver, BigDecimal amount) throws Exception {

        userService.validateTransaction(sender, amount);
        isAuthorized();


        return null;
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
            throw new Exception("Error while communicating with authorization server", e)
        } catch (RestClientException e) {
            throw new Exception("Connection error", e);
        }
    }
}
