package com.dev.spfcbank.domain.transaction;

import com.dev.spfcbank.domain.user.User;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(BigDecimal value,
                             UUID sender,
                             UUID receiver) {
}
