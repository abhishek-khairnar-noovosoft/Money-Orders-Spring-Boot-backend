package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.repositories.UserRepository
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

    fun getUserSpecificTransactions(id: Long): List<Transaction> {
        return transactionRepository.findAllByWithdrawFromOrDepositTo(id, id)
    }
}