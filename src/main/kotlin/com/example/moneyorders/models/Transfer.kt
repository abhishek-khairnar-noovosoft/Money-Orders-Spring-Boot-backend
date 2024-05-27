package com.example.moneyorders.models

import java.math.BigInteger

class Transfer(
        val withdrawFrom: Long,
        val depositTo : Long,
        val transactionAmount : BigInteger
)