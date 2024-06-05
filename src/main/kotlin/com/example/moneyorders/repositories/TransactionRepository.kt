package com.example.moneyorders.repositories

import com.example.moneyorders.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.sql.Timestamp
import java.time.LocalDateTime

interface TransactionRepository : JpaRepository<Transaction, Int> {
    @Query("""
        SELECT * FROM transactions t WHERE t.status = 'PENDING' LIMIT :limit
    """, nativeQuery = true)
    fun getNoOfRequiredTransactionsToProcess(limit : Int) : List<Transaction>

    fun findById(id : Long?): Transaction

    fun findAllByWithdrawFromOrDepositTo(withdrawFrom: Long, depositTo: Long) : List<Transaction>


}