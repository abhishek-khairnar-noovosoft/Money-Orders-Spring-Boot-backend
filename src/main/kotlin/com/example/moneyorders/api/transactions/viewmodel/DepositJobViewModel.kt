package com.example.moneyorders.api.transactions.viewmodel

import java.math.BigInteger

data class DepositJobViewModel(
        val depositTo : Long,
        val transactionAmount : BigInteger
)