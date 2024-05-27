package com.example.moneyorders.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate


@Entity
@Table(name = "transactions")
data class Transaction(
        @Id

        @Column(name = "withdraw_from", nullable = true)
        val withdrawFrom: Long? = null,

        @Column(name = "deposited_to", nullable = true)
        val depositedTo: Long? = null,

        @Column(name = "transaction_amount", nullable = false)
        val transactionAmount: BigInteger = BigInteger.ZERO,

        @Column(name = "transaction_type", nullable = false)
        val transactionType: String = "",

        @Column(name = "created_at", nullable = false)
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

        @Column(name = "status", nullable = false)
        var status: String = "",

        @Column(name = "date", nullable = false)
        var date: LocalDate = LocalDate.now()
)