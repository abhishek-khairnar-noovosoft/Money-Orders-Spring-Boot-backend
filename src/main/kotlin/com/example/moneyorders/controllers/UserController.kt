package com.example.moneyorders.controllers


import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.services.JwtService
import com.example.moneyorders.services.TransactionService
import com.example.moneyorders.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(
        private val transactionService: TransactionService,
        private val userService: UserService,
        private val jwtService: JwtService
){

    @GetMapping("/profile")
    fun getUserProfile(@AuthenticationPrincipal userDetails: UserDetails) : UserDetails {
        return userDetails
    }
    @GetMapping("/transactions")
    fun getUserSpecificTransactions(@RequestHeader("Authorization")authToken : String) : ResponseEntity<List<Transaction>> {
        try {
            val token = authToken.split(" ")[1]

            val email = jwtService.getUsernameFromToken(token)
            val user = userService.getUserByEmail(email)
            println(email)
            if (user != null)
                return ResponseEntity.ok().body(transactionService.getUserSpecificTransactions(user.id))
            else
                throw IllegalArgumentException()
        } catch (ex: IllegalArgumentException) {
            throw IllegalArgumentException("Invalid token format")
        }
    }
}