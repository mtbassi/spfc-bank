package com.dev.spfcbank.infra;

import com.dev.spfcbank.domain.exception.ExceptionDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threadDuplicateEntry(DataIntegrityViolationException exception){
        ExceptionDTO dto = new ExceptionDTO("User already exists", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity threadTransactionSystem(TransactionSystemException exception){
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity generalException(Exception exception){
        ExceptionDTO dto = new ExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(dto);
    }
}
