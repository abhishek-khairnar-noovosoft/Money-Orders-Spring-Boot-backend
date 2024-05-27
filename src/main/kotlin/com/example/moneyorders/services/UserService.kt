package com.example.moneyorders.services

import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import com.example.moneyorders.exceptions.CustomExceptions.UsernameNotFoundException
import org.springframework.security.core.userdetails.User

@Service
class UserService(val userRepository: UserRepository,val transactionRepository: TransactionRepository) :UserDetailsService {

    fun getUserByEmail(email: String) : UserEntity? {
        return userRepository.findByEmail(email)
    }
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("User not found with username: $email")

        return User(user.email, user.password, emptyList())
    }

}