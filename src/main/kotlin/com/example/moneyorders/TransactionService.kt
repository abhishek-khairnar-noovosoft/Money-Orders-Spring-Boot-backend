package com.example.moneyorders

import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class TransactionService {
    private val transactions = mutableListOf<Transaction>()

    fun getAllTransactions(): Iterable<Transaction> =
            transactions

    fun deposit(transaction: DepositTransactionDTO): Transaction {
        if (transaction.transactionAmount <= 0)
            throw IllegalArgumentException("transaction amount cannot be zero")

        val id = getNewId()
        val depositTo = transaction.depositTo
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                id = id,
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = null,
                transactionType = "deposit",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "pending"
        )
        transactions.add(transactionModel)
        return transactionModel
    }


    fun withdraw(transaction: WithdrawTransactionDTO): Transaction {
        if (transaction.transactionAmount <= 0)
            throw IllegalArgumentException("transaction amount cannot be zero")

        val id = getNewId()
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                id = id,
                depositedTo = null,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "withdraw",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "pending"
        )
        transactions.add(transactionModel)
        return transactionModel
    }

    fun transfer(transaction: TransferTransactionDTO): Transaction {
        if (transaction.transactionAmount <= 0)
            throw IllegalArgumentException("transaction amount cannot be zero")

        val id = getNewId()
        val depositTo = transaction.depositTo
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                id = id,
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "transfer",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "pending"
        )
        transactions.add(transactionModel)
        return transactionModel
    }


    fun getNewId(): Int = transactions.size

}