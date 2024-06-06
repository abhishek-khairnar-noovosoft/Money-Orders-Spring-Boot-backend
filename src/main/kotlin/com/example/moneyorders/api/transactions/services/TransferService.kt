package com.example.moneyorders.api.transactions.services

import com.example.moneyorders.api.jobs.model.TransferJob
import com.example.moneyorders.api.jobs.model.TransferJobData
import com.example.moneyorders.api.jobs.repository.TransferRepository
import com.example.moneyorders.api.transactions.viewmodel.TransferJobViewModel
import com.example.moneyorders.entities.Transaction
import com.example.moneyorders.repositories.TransactionRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDate

@Service
class TransferService(
        val transferRepository: TransferRepository,
        val transactionRepository: TransactionRepository
) {
    fun createTransferJob(
            transferJobViewModel: TransferJobViewModel
    ) :Transaction{
        val transferJob = TransferJob()
        transferJob.withData(
                TransferJobData(
                        withdrawFrom = transferJobViewModel.withdrawFrom,
                        depositTo = transferJobViewModel.depositTo,
                        transactionAmount = transferJobViewModel.transactionAmount
                )
        )
        val transaction = transfer(transferJobViewModel)
        transferJob.transactionId = transaction.id
        transferRepository.save(transferJob)
        return transaction
    }
    fun transfer(transaction: TransferJobViewModel): Transaction {

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