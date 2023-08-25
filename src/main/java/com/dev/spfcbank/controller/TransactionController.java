package com.dev.spfcbank.controller;

import com.dev.spfcbank.domain.transaction.Transaction;
import com.dev.spfcbank.domain.transaction.TransactionDTO;
import com.dev.spfcbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO data) throws Exception {
        return new ResponseEntity<>(transactionService.createTransaction(data), HttpStatus.CREATED);
    }
}
