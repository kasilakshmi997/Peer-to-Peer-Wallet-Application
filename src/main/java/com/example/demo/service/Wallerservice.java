package com.example.demo.service;

import com.example.demo.model.user;
import com.example.demo.model.Transaction;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class Wallerservice {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public Wallerservice(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public String registerUser(String name, String email, double balance, String pin) {
        if (userRepository.findByEmail(email) != null) return "Error: Email already exists";
        user newUser = new user();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setBalance(balance);
        newUser.setPin(pin);
        userRepository.save(newUser);
        return "Success: Account Created";
    }

    @Transactional
    public String transferMoney(String from, String to, double amount, String pin) {
        user sender = userRepository.findByEmail(from);
        user receiver = userRepository.findByEmail(to);

        // 1. Basic validation
        if (sender == null || receiver == null) return "Error: Account not found";
        if (from.equals(to)) return "Error: Cannot send money to yourself";

        // 2. NULL-SAFE PIN VERIFICATION
        // This logic handles both: missing PINs in database and incorrect PINs from user
        if (sender.getPin() == null || !sender.getPin().equals(pin)) {
            return "Error: Incorrect or missing Transaction PIN";
        }

        // 3. Balance Check
        if (sender.getBalance() < amount) return "Error: Insufficient balance";

        // 4. Update and Save
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        transactionRepository.save(new Transaction(from, to, amount));
        userRepository.save(sender);
        userRepository.save(receiver);

        return "Success: Payment Completed";
    }

    public List<user> getAllUsers() {
        // Uses JpaRepository's built-in findAll() method to get every user
        return userRepository.findAll();
    }

	public List<Transaction> getHistory(String email) {
	    // Returns the list of transactions for this user, sorted by newest first
	    return transactionRepository.findBySenderEmailOrReceiverEmailOrderByTimestampDesc(email, email);
	}
}