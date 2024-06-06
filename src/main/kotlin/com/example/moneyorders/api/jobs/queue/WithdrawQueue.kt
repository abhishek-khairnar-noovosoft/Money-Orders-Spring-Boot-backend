package com.example.moneyorders.api.jobs.queue

import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.model.WithdrawJob
import com.example.moneyorders.api.jobs.repository.JobRepository
import com.example.moneyorders.api.jobs.repository.WithdrawRepository
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class WithdrawQueue(
        jobRepository: JobRepository,
        val withdrawRepository: WithdrawRepository,
        val userRepository: UserRepository,
        val transactionRepository: TransactionRepository,

        ) : QueueJob(
        jobRepository = jobRepository
) {
    override fun handle(job: Job) {
        job as WithdrawJob

        val transaction = transactionRepository.findById(job.transactionId)
        val user = userRepository.findById(transaction.withdrawFrom)

        if (transaction.transactionAmount <= BigInteger.ZERO) {
            throw CustomExceptions.InvalidAmountException("Amount cannot be less than zero")
        }

        if (user.balance < transaction.transactionAmount) {
            transaction.status = Status.FAILED.toString()
            transactionRepository.save(transaction)
            throw CustomExceptions.InvalidAmountException("insufficient balance")
        }
        else {
            user.balance -= transaction.transactionAmount
            userRepository.save(user)

            transaction.status = "SUCCESS"
            transactionRepository.save(transaction)

            job.status = Status.SUCCESS
            withdrawRepository.save(job)
        }

    }

}