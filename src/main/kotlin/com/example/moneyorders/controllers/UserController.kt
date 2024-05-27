package com.example.moneyorders.controllers

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.services.TransactionService
import com.example.moneyorders.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(private val userService: UserService,private val transactionService: TransactionService) {

    @GetMapping("/profile")
    fun getUserProfile(@AuthenticationPrincipal userDetails: UserDetails) : UserDetails {
        return userDetails
    }

    @GetMapping("/transactions")
    fun getUserSpecificTransactions(@AuthenticationPrincipal userDetails: UserDetails) : Iterable<Transaction>?{
        return transactionService.getUserSpecificTransactions(userDetails.username)
    }

}