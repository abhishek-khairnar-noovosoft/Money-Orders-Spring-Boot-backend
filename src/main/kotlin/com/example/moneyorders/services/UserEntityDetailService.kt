package com.example.moneyorders.services

import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserEntityDetailService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        println("user loaded successfully $email")
        val user: UserEntity? = userRepository.findByEmail(email)
        if (user != null) {
            return User.builder()
                    .username(user.email)
                    .password(user.password)
                    .roles(user.role)
                    .build()
        } else {
            throw UsernameNotFoundException("no user with email $email")
        }
    }

}