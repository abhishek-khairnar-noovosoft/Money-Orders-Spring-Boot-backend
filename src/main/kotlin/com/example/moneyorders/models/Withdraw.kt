package com.example.moneyorders.models

import java.math.BigInteger

class Withdraw(
        val withdrawFrom: Long,
        val transactionAmount: BigInteger
)