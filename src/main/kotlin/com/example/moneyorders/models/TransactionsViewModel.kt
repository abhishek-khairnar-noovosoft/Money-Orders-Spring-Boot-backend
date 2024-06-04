package com.example.moneyorders.models

import java.math.BigInteger
import javax.swing.text.View

class TransactionsViewModel {

    data class DepositViewModel(
            val depositTo: Long,
            val transactionAmount: BigInteger
    )
    data class TransferViewModel(
            val withdrawFrom: Long,
            val depositedTo : Long,
            val transactionAmount : BigInteger
    )

    data class UserViewModel(
            val email : String,
            val password : String
    )

    data class WithdrawViewModel(
            val withdrawFrom: Long,
            val transactionAmount: BigInteger
    )

}