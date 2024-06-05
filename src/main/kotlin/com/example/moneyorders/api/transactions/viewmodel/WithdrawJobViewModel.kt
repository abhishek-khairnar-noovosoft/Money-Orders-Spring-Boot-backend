package com.example.moneyorders.api.transactions.viewmodel

import java.math.BigInteger

data class WithdrawJobViewModel(
        val withdrawFrom : Long,
        val transactionAmount : BigInteger
)