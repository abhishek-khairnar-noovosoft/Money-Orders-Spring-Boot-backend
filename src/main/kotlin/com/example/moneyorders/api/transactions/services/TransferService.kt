package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.api.jobs.model.*
import com.example.moneyorders.api.jobs.repository.TransferRepository
import com.example.moneyorders.api.jobs.repository.WithdrawRepository
import com.example.moneyorders.api.transactions.viewmodel.TransferViewModel
import com.example.moneyorders.api.transactions.viewmodel.WithdrawViewModel
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.models.TransactionsViewModel
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class TransferService(
        private val transactionRepository: TransactionRepository,
        private val transferRepository: TransferRepository
) {

    @Transactional
    fun createTransferJob(
            transferViewModel: TransferViewModel
    ){
        val transferJob = TransferJob()
        transferJob.withData(
                TransferJobData(
                        withdrawFrom = transferViewModel.withdrawFrom,
                        depositTo = transferViewModel.depositTo,
                        transactionAmount = transferViewModel.transactionAmount
                )
        )

        transfer(transferViewModel)
        transferRepository.save(transferJob)
    }

    fun transfer(transaction: TransferViewModel): Transaction {
        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw CustomExceptions.InvalidAmountException("transaction amount cannot be less than or equal to zero")

        val depositTo = transaction.depositTo
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositTo = depositTo,
                transactionAmount = transactionAmount,
                withdrawFrom = withdrawFrom,
                transactionType = "TRANSFER",
                createdAt = Timestamp(System.currentTimeMillis()),
                status = "PENDING",
                date = LocalDate.now()
        )

        transactionRepository.save(transactionModel)
        return transactionModel
    }

}