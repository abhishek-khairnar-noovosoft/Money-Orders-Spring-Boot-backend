package com.example.moneyorders.controllers

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ManagerController @Autowired constructor(
        private val transactionService: TransactionService,
) {
    @GetMapping("/")
    fun home(): String {
        return "hello"
    }

    @GetMapping("/AllTransactions")
    fun getAllTransactions(): ResponseEntity<Iterable<Transaction>> {
        return ResponseEntity.ok().body(transactionService.getAllTransactions())
    }

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<Iterable<UserEntity>> {
        return ResponseEntity.ok().body(transactionService.getAllUsers())
    }
}