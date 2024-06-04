package com.example.moneyorders.api.transactions.viewmodel

import java.math.BigInteger

data class TransferViewModel(
        val withdrawFrom : Long,
        val depositTo : Long,
        val transactionAmount : BigInteger
)