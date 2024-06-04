package com.example.moneyorders.controllers

import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.models.TransactionsViewModel.DepositViewModel
import com.example.moneyorders.models.TransactionsViewModel.WithdrawViewModel
import com.example.moneyorders.models.TransactionsViewModel.TransferViewModel
import com.example.moneyorders.services.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

//    @PostMapping("/deposit")
//    fun depositTransaction(@RequestBody transaction: DepositViewModel): ResponseEntity<Transaction> {
//        return ResponseEntity.ok().body(transactionService.deposit(transaction))
//    }

    @PostMapping("/withdraw")
    fun withdrawTransaction(@RequestBody transaction: WithdrawViewModel): ResponseEntity<Transaction> {
        return ResponseEntity.ok().body(transactionService.withdraw(transaction))
    }

    @PostMapping("/transfer")
    fun transferTransaction(@RequestBody transaction: TransferViewModel): ResponseEntity<Transaction> {
        return ResponseEntity.ok().body(transactionService.transfer(transaction))
    }
}