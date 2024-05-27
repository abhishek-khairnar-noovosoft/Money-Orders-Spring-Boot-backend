package com.example.moneyorders.services

import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class UserService(val userRepository: UserRepository,val transactionRepository: TransactionRepository) {

    fun getUserByEmail(email: String) : UserEntity? {
        return userRepository.findByEmail(email)
    }

}