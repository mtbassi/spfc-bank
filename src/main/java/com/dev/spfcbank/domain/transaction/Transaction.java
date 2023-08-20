package com.dev.spfcbank.domain.transaction;

import com.dev.spfcbank.domain.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "transactions")
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "receiver_id/")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id/")
    private User receiver;

}
