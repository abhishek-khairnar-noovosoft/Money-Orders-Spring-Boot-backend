package com.example.moneyorders.api.jobs.queue

import com.example.moneyorders.api.jobs.model.DepositJob
import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.repository.DepositRepository
import com.example.moneyorders.api.jobs.repository.JobRepository
import com.example.moneyorders.exceptions.CustomExceptions
import com.example.moneyorders.repositories.TransactionRepository
import com.example.moneyorders.repositories.UserRepository
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class DepositQueue(
        override val jobRepository: JobRepository,
        val depositRepository: DepositRepository,
        val userRepository: UserRepository,
        val transactionRepository: TransactionRepository

) : QueueJob(
        jobRepository = jobRepository
) {
    override fun handle(job: Job) {
        job as DepositJob

        val transaction = transactionRepository.findById(job.transactionId)

        if(transaction.transactionAmount <= BigInteger.ZERO){
            transaction.status = Status.FAILED.toString()
            transactionRepository.save(transaction)

            throw CustomExceptions.InvalidAmountException("Amount cannot be less than or equal to zero")
        }
        else{
            val user = userRepository.findById(transaction.depositTo)
            user.balance += transaction.transactionAmount
            userRepository.save(user)

            transaction.status = "SUCCESS"
            transactionRepository.save(transaction)

            job.status = Status.SUCCESS
            depositRepository.save(job)
        }


    }
}