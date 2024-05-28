package com.example.moneyorders.entities

import jakarta.persistence.*
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate


@Entity
@Table(name = "transactions")
data class Transaction(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id:Long = 0,
        @Column(name = "withdraw_from")
        val withdrawFrom: Long? = null,

        @Column(name = "deposited_to")
        val depositedTo: Long? = null,

        @Column(name = "transaction_amount")
        val transactionAmount: BigInteger = BigInteger.ZERO,

        @Column(name = "transaction_type",)
        val transactionType: String = "",

        @Column(name = "created_at")
        val createdAt: Timestamp = Timestamp(System.currentTimeMillis()),

        @Column(name = "status")
        var status: String = "",

        @Column(name = "date", nullable = false)
        var date: LocalDate = LocalDate.now()
)