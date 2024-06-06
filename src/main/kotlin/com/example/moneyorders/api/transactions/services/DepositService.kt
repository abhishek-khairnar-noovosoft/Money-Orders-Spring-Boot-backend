package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.api.transactions.viewmodel.DepositJobViewModel
import com.example.moneyorders.api.jobs.model.CustomDepositJobData
import com.example.moneyorders.api.jobs.model.DepositJob
import com.example.moneyorders.api.jobs.repository.DepositRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class DepositService(
        val depositRepository : DepositRepository,
        val transactionRepository: TransactionRepository
) {
    @Transactional
    fun createCustomDepositJob(
            depositViewModel : DepositJobViewModel
    ) : Transaction {
        val depositJob = DepositJob()
        depositJob.withData(
                CustomDepositJobData(
                        depositTo = depositViewModel.depositTo,
                        transactionAmount = depositViewModel.transactionAmount
                )
        )
        val transaction = deposit(depositViewModel)
        depositJob.transactionId = transaction.id
        depositRepository.save(depositJob)
        return transaction
    }

    fun deposit(transaction: DepositJobViewModel): Transaction {

        val depositTo = transaction.depositTo
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositTo =  depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = null,
                transactionType = "DEPOSIT",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "PENDING",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

}