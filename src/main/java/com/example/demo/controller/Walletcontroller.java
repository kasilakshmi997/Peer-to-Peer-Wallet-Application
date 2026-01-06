package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.model.user;
import com.example.demo.service.Wallerservice;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class Walletcontroller {

    private final Wallerservice wallerservice;

    public Walletcontroller(Wallerservice wallerservice) {
        this.wallerservice = wallerservice;
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email, 
                           @RequestParam double balance, @RequestParam String pin) {
        return wallerservice.registerUser(name, email, balance, pin);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam String from, @RequestParam String to, 
                           @RequestParam double amount, @RequestParam String pin) {
        return wallerservice.transferMoney(from, to, amount, pin);
    }

    // KEEP ONLY THIS ONE - DELETE getUsers1() IF IT EXISTS
    @GetMapping("/users")
    public List<user> getUsers() {
        return wallerservice.getAllUsers();
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam String email) {
        return wallerservice.getHistory(email);
    }
    @PostMapping("/api/wallet/transfer")
    public String transferMoney(@RequestBody ServerRequest request) {
        // Your logic to deduct from sender and add to receiver
        return "Transfer of ₹" + ((Transaction) request).getAmount() + " successful!";
    }
}