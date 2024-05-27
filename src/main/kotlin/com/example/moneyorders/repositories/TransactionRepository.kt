package com.example.moneyorders.repositories

import com.example.moneyorders.entities.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TransactionRepository : JpaRepository<Transaction, Int> {

    @Query("""
        SELECT t.id    AS transaction_id,
                                      u.name  AS withdraw_from,
                                      dt.name AS deposited_to,
                                      t.transaction_type,
                                      t.transaction_amount,
                                      t.date,
                                      t.status
                               FROM transactions t
                                        LEFT JOIN users u ON t.withdraw_from = u.id
                                        LEFT JOIN users dt ON t.deposited_to = dt.id
                               WHERE t.withdraw_from = :id
                                  OR t.deposited_to = :id
                               ORDER BY t.id desc;
    """, nativeQuery = true)
    fun getTransactionsOfUserWith(id: Long): Iterable<Transaction>


}