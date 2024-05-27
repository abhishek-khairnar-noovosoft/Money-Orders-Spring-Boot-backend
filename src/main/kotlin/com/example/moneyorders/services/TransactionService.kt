package com.example.moneyorders.services

import com.example.moneyorders.models.TransactionsViewModel.DepositViewModel
import com.example.moneyorders.models.TransactionsViewModel.WithdrawViewModel
import com.example.moneyorders.models.TransactionsViewModel.TransferViewModel
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.exceptions.CustomExceptions.*
import com.example.moneyorders.repositories.UserRepository
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate


@Service
class TransactionService(
        val transactionRepository: TransactionRepository,
        val userRepository: UserRepository
) {

    fun getAllUsers(): Iterable<UserEntity> {
        return userRepository.findAll()
    }

    fun getAllTransactions(): Iterable<Transaction> {
        return transactionRepository.findAll()
    }

    fun deposit(transaction: DepositViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be zero")

        val balance = userRepository.getBalance(transaction.depositTo)
        val depositTo = transaction.depositTo
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = null,
                transactionType = "deposit",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "success",
                date = LocalDate.now()
        )

        userRepository.deposit(balance, transactionAmount, depositTo)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun withdraw(transaction: WithdrawViewModel): Transaction {
        println(transaction.transactionAmount)
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be zero")

        val balance = userRepository.getBalance(transaction.withdrawFrom)
        if(balance < transaction.transactionAmount){
            throw IllegalArgumentException("insufficient balance")
        }

        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = null,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "withdraw",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "success",
                date = LocalDate.now()
        )
        userRepository.withdraw(balance,transactionAmount,withdrawFrom)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun transfer(transaction: TransferViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be zero")

        val balance = userRepository.getBalance(transaction.withdrawFrom)
        if(balance < transaction.transactionAmount){
            throw IllegalArgumentException("insufficient balance")
        }
        if(transaction.withdrawFrom == transaction.depositTo){
            throw IllegalArgumentException("sender and receiver cannot be same")
        }

        val depositTo = transaction.depositTo
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "transfer",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "success",
                date = LocalDate.now()
        )
        userRepository.withdraw(balance, transactionAmount, withdrawFrom)
        userRepository.deposit(balance, transactionAmount, depositTo)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

}