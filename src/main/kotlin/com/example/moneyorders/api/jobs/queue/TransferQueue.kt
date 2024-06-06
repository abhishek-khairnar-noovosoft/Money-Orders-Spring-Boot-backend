package com.example.moneyorders.api.jobs.queue

import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.model.TransferJob
import com.example.moneyorders.api.jobs.repository.JobRepository
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class TransferQueue(
        jobRepository: JobRepository,
        val transactionRepository: TransactionRepository,
        val userRepository: UserRepository
) : QueueJob(
        jobRepository = jobRepository
) {
    override fun handle(job: Job) {
        job as TransferJob

        val transaction = transactionRepository.findById(job.transactionId)

        if(transaction.transactionAmount <= BigInteger.ZERO){
            transaction.status = Status.FAILED.toString()
            transactionRepository.save(transaction)
            throw CustomExceptions.InvalidAmountException("amount cannot be less than or equal to zero")
        }
        else {
            val withdrawFrom = userRepository.findById(transaction.withdrawFrom)
            val depositTo = userRepository.findById(transaction.depositTo)

            if(withdrawFrom.balance < transaction.transactionAmount){
                transaction.status = Status.FAILED.toString()
                transactionRepository.save(transaction)
                throw CustomExceptions.InvalidAmountException("Insufficient Balance")
            }
            else if(transaction.withdrawFrom == transaction.depositTo){
                transaction.status = Status.FAILED.toString()
                transactionRepository.save(transaction)
                throw IllegalArgumentException("Cannot transfer between same accounts")
            }
            else{

                withdrawFrom.balance -= transaction.transactionAmount
                depositTo.balance += transaction.transactionAmount

                userRepository.save(withdrawFrom)
                userRepository.save(depositTo)

                job.status = Status.SUCCESS
                jobRepository.save(job)
            }
        }
    }
}