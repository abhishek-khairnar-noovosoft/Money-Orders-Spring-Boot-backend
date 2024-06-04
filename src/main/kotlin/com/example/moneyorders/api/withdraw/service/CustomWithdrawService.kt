package com.example.moneyorders.api.withdraw.service

import com.example.moneyorders.api.jobs.model.CustomWithdrawJob
import com.example.moneyorders.api.jobs.model.CustomWithdrawJobData
import com.example.moneyorders.api.jobs.repository.CustomWithdrawRepository
import com.example.moneyorders.api.withdraw.viewmodel.CustomWithdrawViewModel
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class CustomWithdrawService(
        val transactionRepository: TransactionRepository,
        val customWithdrawRepository: CustomWithdrawRepository
) {

    @Transactional
    fun createCustomWithdrawJob(
            customWithdrawViewModel: CustomWithdrawViewModel
    ) {
        val customWithdrawJob = CustomWithdrawJob()
        customWithdrawJob.withData(
                CustomWithdrawJobData(
                        withdrawFrom = customWithdrawViewModel.withdrawFrom,
                        transactionAmount = customWithdrawViewModel.transactionAmount
                )
        )
        withdraw(customWithdrawViewModel)
        customWithdrawRepository.save(customWithdrawJob)
    }

    fun withdraw(transaction: CustomWithdrawViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw CustomExceptions.InvalidAmountException("transaction amount cannot be less than or equal to zero")


        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositedTo = null,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "withdraw",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "processing",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }
}