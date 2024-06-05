package com.example.moneyorders.api.jobs.queue

import com.example.moneyorders.api.jobs.model.DepositJob
import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.repository.DepositRepository
import com.example.moneyorders.api.jobs.repository.JobRepository
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

        val id:Long = job.data.toString().split(", ")[0].split(" ")[1].toLong()
        val amount : BigInteger = job.data.toString().split(", ")[1].split(" ")[1].split("}")[0].toBigInteger()

        val user = userRepository.findById(id)
        user.balance += amount
        userRepository.save(user)

        val transaction = transactionRepository.findById(job.transactionId)
        transaction.status = "SUCCESS"
        transactionRepository.save(transaction)

        job.status = Status.SUCCESS
        depositRepository.save(job)
    }
}