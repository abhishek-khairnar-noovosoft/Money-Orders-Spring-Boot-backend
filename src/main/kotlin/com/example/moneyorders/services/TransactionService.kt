package com.example.moneyorders.services

import com.example.moneyorders.models.TransactionsViewModel.DepositViewModel
import com.example.moneyorders.models.TransactionsViewModel.WithdrawViewModel
import com.example.moneyorders.models.TransactionsViewModel.TransferViewModel
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.exceptions.CustomExceptions.*
import com.example.moneyorders.repositories.UserRepository
import jakarta.transaction.Transactional
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
            throw InvalidAmountException("transaction amount cannot be less than or equal to zero")

        val depositTo = transaction.depositTo
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = null,
                transactionType = "deposit",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "processing",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun withdraw(transaction: WithdrawViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be less than or equal to zero")


        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = null,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "withdraw",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "processing",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun transfer(transaction: TransferViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw InvalidAmountException("transaction amount cannot be less than or equal to zero")

        val depositTo = transaction.depositTo
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "transfer",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "processing",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

    fun  getLatestProcessingTransactions(limit : Int) : List<Transaction>{
        return transactionRepository.getNoOfRequiredTransactionsToProcess(limit)
    }

    @Transactional
    fun processTransaction(id : Long){
        println("processing $id")
        val transaction = transactionRepository.findById(id)
            when(transaction.transactionType){
                "deposit" -> {
                    val user = userRepository.findById(transaction.depositedTo)
                    user.balance += transaction.transactionAmount
                    userRepository.save(user)
                    transaction.status = "success"
                    transactionRepository.save(transaction)
                }
                "withdraw" ->{
                    val user = userRepository.findById(transaction.withdrawFrom)
                    if(user.balance < transaction.transactionAmount){
                        transaction.status = "failed"
                        transactionRepository.save(transaction)
                        throw IllegalArgumentException("insufficient balance")
                    }
                    user.balance -= transaction.transactionAmount
                    transaction.status = "success"
                    transactionRepository.save(transaction)
                    userRepository.save(user)
                }
                "transfer" -> {
                    val sender = userRepository.findById(transaction.withdrawFrom)
                    val receiver = userRepository.findById(transaction.depositedTo)
                    if(sender.balance < transaction.transactionAmount){
                        transaction.status = "failed"
                        transactionRepository.save(transaction)
                        throw IllegalArgumentException("insufficient balance")
                    }
                    if(transaction.withdrawFrom == transaction.depositedTo){
                        transaction.status = "failed"
                        transactionRepository.save(transaction)
                        throw IllegalArgumentException("sender and receiver cannot be same")
                    }
                    sender.balance -= transaction.transactionAmount
                    receiver.balance += transaction.transactionAmount
                    userRepository.save(sender)
                    userRepository.save(receiver)
                    transactionRepository.save(transaction)
                } else ->{
                    transaction.status = "failed"
                    transactionRepository.save(transaction)
                }
            
        }
    }

    fun getUserSpecificTransactions(id : Long) : List<Transaction>{
        println(transactionRepository.findAllByWithdrawFromOrDepositedTo(id,id))
        return transactionRepository.findAllByWithdrawFromOrDepositedTo(id,id)
    }
}