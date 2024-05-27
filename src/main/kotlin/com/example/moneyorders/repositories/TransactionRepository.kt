package com.example.moneyorders.repositories

import com.example.moneyorders.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TransactionRepository : JpaRepository<Transaction, Int> {

//    fun getTransactionsBy(email: String) : Iterable<Transaction>

    @Query("""
        SELECT * FROM transactions t;
    """, nativeQuery = true)
    fun getTransactionsOfUserWith(id: Long): Iterable<Transaction>?


}