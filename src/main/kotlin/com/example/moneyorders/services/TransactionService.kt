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

        val user = userRepository.findById(transaction.depositTo)
        val balance = user.balance
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


        user.balance += transactionAmount
        userRepository.save(user)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun withdraw(transaction: WithdrawViewModel): Transaction {
        println(transaction.transactionAmount)
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be zero")

        val user = userRepository.findById(transaction.withdrawFrom)
        val balance = user.balance
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
        user.balance -= transactionAmount
        userRepository.save(user)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun transfer(transaction: TransferViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be zero")

        val sender = userRepository.findById(transaction.withdrawFrom)
        val balanceOfSender = sender.balance

        val receiver = userRepository.findById(transaction.depositTo)

        if(balanceOfSender < transaction.transactionAmount){
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

        sender.balance -= transactionAmount
        receiver.balance += transactionAmount

        userRepository.save(sender)
        userRepository.save(receiver)
        transactionRepository.save(transactionModel)
        return transactionModel
    }

}