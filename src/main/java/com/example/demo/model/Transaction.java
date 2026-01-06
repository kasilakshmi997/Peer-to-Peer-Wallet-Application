package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(String senderEmail, String receiverEmail, double amount) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getSenderEmail() { return senderEmail; }
    public String getReceiverEmail() { return receiverEmail; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}