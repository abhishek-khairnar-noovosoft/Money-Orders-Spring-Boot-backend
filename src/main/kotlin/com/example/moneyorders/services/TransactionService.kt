package com.example.moneyorders.services

import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.repositories.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

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



    @Transactional
    fun processTransaction(id: Long?) {
        val transaction = transactionRepository.findById(id)
        when (transaction.transactionType) {
            "DEPOSIT" -> {
                val user = userRepository.findById(transaction.depositTo)
                user.balance += transaction.transactionAmount
                userRepository.save(user)
                transaction.status = "SUCCESS"
                transactionRepository.save(transaction)
            }

            "WITHDRAW" -> {
                val user = userRepository.findById(transaction.withdrawFrom)
                if (user.balance < transaction.transactionAmount) {
                    transaction.status = "FAILED"
                    transactionRepository.save(transaction)
                    throw IllegalArgumentException("insufficient balance")
                }
                user.balance -= transaction.transactionAmount
                transaction.status = "SUCCESS"
                transactionRepository.save(transaction)
                userRepository.save(user)
            }

            "TRANSFER" -> {
                val sender = userRepository.findById(transaction.withdrawFrom)
                val receiver = userRepository.findById(transaction.depositTo)
                if (sender.balance < transaction.transactionAmount) {
                    transaction.status = "FAILED"
                    transactionRepository.save(transaction)
                    throw IllegalArgumentException("insufficient balance")
                }
                if (transaction.withdrawFrom == transaction.depositTo) {
                    transaction.status = "FAILED"
                    transactionRepository.save(transaction)
                    throw IllegalArgumentException("sender and receiver cannot be same")
                }
                sender.balance -= transaction.transactionAmount
                receiver.balance += transaction.transactionAmount
                userRepository.save(sender)
                userRepository.save(receiver)
                transactionRepository.save(transaction)
            }

            else -> {
                transaction.status = "FAILED"
                transactionRepository.save(transaction)
            }

        }
    }

    fun getUserSpecificTransactions(id: Long): List<Transaction> {
        return transactionRepository.findAllByWithdrawFromOrDepositTo(id, id)
    }
}