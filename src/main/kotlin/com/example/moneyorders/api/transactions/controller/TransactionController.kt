package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.DepositService
import com.example.moneyorders.api.transactions.services.TransferService
import com.example.moneyorders.api.transactions.services.WithdrawService
import com.example.moneyorders.api.transactions.viewmodel.DepositJobViewModel
import com.example.moneyorders.api.transactions.viewmodel.TransferJobViewModel
import com.example.moneyorders.api.transactions.viewmodel.WithdrawJobViewModel
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.api.transactions.services.TransactionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController(
        val depositService: DepositService,
        val withdrawService: WithdrawService,
        val transferService: TransferService,
        val transactionService: TransactionService
) {
    @PostMapping("/deposit")
    fun deposit(
            @RequestBody depositJobViewModel: DepositJobViewModel
    ) : ResponseEntity<Any?> {
        val transaction = depositService.createCustomDepositJob(depositJobViewModel)
        return ResponseEntity.ok().body(transaction)
    }
    @PostMapping("/withdraw")
    fun withdraw(
            @RequestBody withdrawJobViewModel: WithdrawJobViewModel
    ) : ResponseEntity<Any?>{
        val transaction = withdrawService.createWithdrawJob(withdrawJobViewModel)
        return ResponseEntity.ok().body(transaction)
    }

    @PostMapping("/transfer")
    fun transfer(
            @RequestBody transferJobViewModel: TransferJobViewModel
    ) : ResponseEntity<Any?>{
        val transaction = transferService.createTransferJob(transferJobViewModel)
        return ResponseEntity.ok().body(transaction)
    }

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