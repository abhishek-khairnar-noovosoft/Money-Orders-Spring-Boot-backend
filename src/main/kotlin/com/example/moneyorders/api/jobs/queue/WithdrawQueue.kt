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
){
    override fun handle(job: Job) {
        job as WithdrawJob

        val id:Long = job.data.toString().split(", ")[0].split(" ")[1].toLong()
        val amount : BigInteger = job.data.toString().split(", ")[1].split(" ")[1].split("}")[0].toBigInteger()

        val user = userRepository.findById(id)
        val transaction = transactionRepository.findById(job.transactionId)

        if(transaction.transactionAmount <= BigInteger.ZERO){
            throw CustomExceptions.InvalidAmountException("Amount cannot be less than zero")
        }

        if(user.balance < transaction.transactionAmount){
            throw CustomExceptions.InvalidAmountException("insufficient balance")
        }

        user.balance -= transaction.transactionAmount
        userRepository.save(user)

        transaction.status = "SUCCESS"
        transactionRepository.save(transaction)

        job.status = Status.SUCCESS
        withdrawRepository.save(job)
    }

}