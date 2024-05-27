package com.example.moneyorders.controllers

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.models.TransactionsViewModel.DepositViewModel
import com.example.moneyorders.models.TransactionsViewModel.WithdrawViewModel
import com.example.moneyorders.models.TransactionsViewModel.TransferViewModel
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
    fun getAllUsers(): Iterable<UserEntity> {
        return transactionService.getAllUsers()
    }

    @PostMapping("/deposit")
    fun depositTransaction(@RequestBody transaction: DepositViewModel): Transaction {
        return transactionService.deposit(transaction)
    }

    @PostMapping("/withdraw")
    fun withdrawTransaction(@RequestBody transaction: WithdrawViewModel): Transaction {
        return transactionService.withdraw(transaction)
    }

    @PostMapping("/transfer")
    fun transferTransaction(@RequestBody transaction: TransferViewModel): Transaction {
        return transactionService.transfer(transaction)
    }
}



// remaining apis
// integrate postgres
// dockerize
// JPA library to integrate with database
// add dependency in gradle


//change Models to ViewModels and keep all in one file
//