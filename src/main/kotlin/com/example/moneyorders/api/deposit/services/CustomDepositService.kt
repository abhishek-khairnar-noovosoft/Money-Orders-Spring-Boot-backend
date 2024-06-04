package com.example.moneyorders.api.deposit.services

import com.example.moneyorders.api.deposit.viewmodel.CustomDepositJobViewModel
import com.example.moneyorders.api.jobs.model.CustomDepositJob
import com.example.moneyorders.api.jobs.model.CustomDepositJobData
import com.example.moneyorders.api.jobs.repository.CustomDepositRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class CustomDepositService(
        val customDepositRepository : CustomDepositRepository,
        val transactionRepository: TransactionRepository
) {
    @Transactional
    fun createCustomDepositJob(
            customDepositViewModel : CustomDepositJobViewModel
    ){
        val customDepositJob = CustomDepositJob()
        customDepositJob.withData(
                CustomDepositJobData(
                        depositTo = customDepositViewModel.depositTo,
                        transactionAmount = customDepositViewModel.transactionAmount
                )
        )

        deposit(customDepositViewModel)
        customDepositRepository.save(customDepositJob)
    }

    fun deposit(transaction: CustomDepositJobViewModel): Transaction {

        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw CustomExceptions.InvalidAmountException("transaction amount cannot be less than or equal to zero")

        val depositTo = transaction.depositTo
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo =  depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = null,
                transactionType = "deposit",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "processing",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

}