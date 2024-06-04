package com.example.moneyorders.api.deposit.viewmodel

import java.math.BigInteger

data class CustomDepositJobViewModel(
        val depositTo : Long,
        val transactionAmount : BigInteger
)