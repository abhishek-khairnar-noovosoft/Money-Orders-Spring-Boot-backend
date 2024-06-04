package com.example.moneyorders.api.withdraw.viewmodel

import java.math.BigInteger

data class CustomWithdrawViewModel(
        val withdrawFrom : Long,
        val transactionAmount : BigInteger
)