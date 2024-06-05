package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.api.transactions.viewmodel.TransferJobViewModel
import com.example.moneyorders.api.jobs.model.TransferJob
import com.example.moneyorders.api.jobs.model.TransferJobData
import com.example.moneyorders.api.jobs.repository.TransferRepository
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigInteger
import java.sql.Timestamp
import java.time.LocalDate

@Service
class TransferService(
        val transferRepository : TransferRepository,
        val transactionRepository: TransactionRepository
) {
    @Transactional
    fun createCustomTransferJob(
            transferViewModel : TransferJobViewModel
    ){
        val transferJob = TransferJob()
        transferJob.withData(
                TransferJobData(
                        withdrawFrom = transferViewModel.withdrawFrom,
                        depositTo = transferViewModel.depositTo,
                        transactionAmount = transferViewModel.transactionAmount
                )
        )
        val transaction = transfer(transferViewModel)
        transferJob.transactionId = transaction.id
        transferRepository.save(transferJob)
    }

    fun transfer(transaction: TransferJobViewModel): Transaction {

        if (transaction.transactionAmount <= BigInteger.ZERO)
            throw CustomExceptions.InvalidAmountException("transaction amount cannot be less than or equal to zero")

        val depositTo = transaction.depositTo
        val withdrawFrom = transaction.withdrawFrom
        val transactionAmount = transaction.transactionAmount
        val transactionModel = Transaction(
                depositTo =  depositTo,
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