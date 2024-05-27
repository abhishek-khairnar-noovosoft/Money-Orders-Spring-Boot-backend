package com.example.moneyorders.models

import java.math.BigInteger

class Deposit(
        val depositTo: Long,
        val transactionAmount: BigInteger
)