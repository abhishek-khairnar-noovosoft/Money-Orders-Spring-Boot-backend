package com.example.moneyorders.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
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
        val withdrawFrom: Long?,
        @Column(name = "deposited_to")
        val depositedTo:Long?,
        @Column(name = "transaction_amount")
        val transactionAmount: BigInteger,
        @Column(name = "transaction_type")
        val transactionType:String,
        @Column(name = "createdat")
        val createdAt:Timestamp,
        var status:String,
        var date: LocalDate
){
        constructor() : this(0, null, null, BigInteger.ZERO, "", Timestamp(System.currentTimeMillis()), "",LocalDate.now())
}
