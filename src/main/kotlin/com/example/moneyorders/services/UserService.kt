package com.example.moneyorders.services

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository,val transactionRepository: TransactionRepository) {
//    fun getUserSpecificTransactions(@AuthenticationPrincipal userDetails: UserDetails) : Iterable<Transaction>{
//        return transactionRepository.getTransactionsBy(userDetails.username)
////        userRepository.findByEmail(userDetails.username)
//    }
}