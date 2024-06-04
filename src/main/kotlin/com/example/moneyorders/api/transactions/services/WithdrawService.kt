package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.api.jobs.model.*
import com.example.moneyorders.api.jobs.repository.WithdrawRepository
import com.example.moneyorders.api.transactions.viewmodel.WithdrawViewModel
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class WithdrawService(
        private val transactionRepository: TransactionRepository,
        private val withdrawRepository: WithdrawRepository
) {

    @Transactional
    fun createWithdrawJob(
            withdrawViewModel: WithdrawViewModel
    ){
        val withdrawJob = WithdrawJob()
        withdrawJob.withData(
                WithdrawJobData(
                        withdrawFrom = withdrawViewModel.withdrawFrom,
                        transactionAmount = withdrawViewModel.transactionAmount
                )
        )

        withdraw(withdrawViewModel)
        withdrawRepository.save(withdrawJob)
    }

    fun withdraw(transaction: WithdrawViewModel) : Transaction{
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw CustomExceptions.InvalidAmountException("transaction amount cannot be less than or equal to zero")


        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "WITHDRAW",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "PENDING",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

}