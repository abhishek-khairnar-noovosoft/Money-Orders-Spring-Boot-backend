package com.example.moneyorders.repositories

import com.example.moneyorders.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Int> {
    fun findAllByStatusOrderByCreatedAtDesc(status: String) : List<Transaction>
    fun findAllByWithdrawFromOrDepositedTo(withdrawFrom: Long, depositedTo: Long) : List<Transaction>


}