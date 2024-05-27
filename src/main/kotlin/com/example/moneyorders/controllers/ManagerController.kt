package com.example.moneyorders.controllers

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.models.Deposit
import com.example.moneyorders.models.Transfer
import com.example.moneyorders.models.Withdraw
import com.example.moneyorders.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ManagerController @Autowired constructor(private val transactionService: TransactionService) {

    @GetMapping("/")
    fun home(): String =
            "Home"
    @GetMapping("/AllTransactions")
    fun getAllTransactions(): Iterable<Transaction> {
        return transactionService.getAllTransactions()
    }

    @GetMapping("/users")
    fun getAllUsers(): Iterable<UserEntity> =
            transactionService.getAllUsers()

    @PostMapping("/deposit")
    fun depositTransaction(@RequestBody transaction: Deposit): Transaction =
            transactionService.deposit(transaction)

    @PostMapping("/withdraw")
    fun withdrawTransaction(@RequestBody transaction: Withdraw): Transaction =
            transactionService.withdraw(transaction)

    @PostMapping("/transfer")
    fun transferTransaction(@RequestBody transaction: Transfer): Transaction =
            transactionService.transfer(transaction)



}

// remaining apis
// integrate postgres
// dockerize
// JPA library to integrate with database
// add dependency in gradle