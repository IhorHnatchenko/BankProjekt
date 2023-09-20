package org.example.repository;

import org.example.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
