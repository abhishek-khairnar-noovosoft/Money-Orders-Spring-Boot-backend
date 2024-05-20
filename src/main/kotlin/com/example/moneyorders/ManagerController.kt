package com.example.moneyorders

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class ManagerController @Autowired constructor(private val transactionService: TransactionService) {
    @GetMapping("/")
    fun getAllTransactions(): Iterable<Transaction> {
        return transactionService.getAllTransactions()
    }

    @PostMapping("/deposit")
    fun depositTransaction(@RequestBody transaction: DepositTransactionDTO): Transaction =
            transactionService.deposit(transaction)

    @PostMapping("/withdraw")
    fun withdrawTransaction(@RequestBody transaction: WithdrawTransactionDTO): Transaction =
            transactionService.withdraw(transaction)

    @PostMapping("/transfer")
    fun transferTransaction(@RequestBody transaction: TransferTransactionDTO): Transaction =
            transactionService.transfer(transaction)

}