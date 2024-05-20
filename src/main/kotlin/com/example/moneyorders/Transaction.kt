package com.example.moneyorders

import java.sql.Timestamp

data class Transaction(
        val id:Int,
        val withdrawFrom: Int?,
        val depositedTo:Int?,
        val transactionAmount:Int,
        val transactionType:String,
        val createdAt:Timestamp,
        var status:String
){

    // Constructor for deposit
//    constructor(
//            id: Int,
//            depositedTo: Int?,
//            transactionAmount: Int
//    ) : this(
//            id,
//            null, // No withdrawFrom for deposit transaction
//            depositedTo,
//            transactionAmount,
//            "deposit",
//            Timestamp(System.currentTimeMillis()),
//            "Pending"
//    )
}
