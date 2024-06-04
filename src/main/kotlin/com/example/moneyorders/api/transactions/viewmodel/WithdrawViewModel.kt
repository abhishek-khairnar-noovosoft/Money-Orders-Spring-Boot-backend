package com.example.moneyorders.api.transactions.viewmodel

import java.math.BigInteger

data class WithdrawViewModel(
        val withdrawFrom : Long,
        val transactionAmount : BigInteger
)