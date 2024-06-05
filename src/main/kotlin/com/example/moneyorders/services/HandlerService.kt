package com.example.moneyorders.services

import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.repository.DepositRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.moneyorders.api.jobs.repository.JobRepository
import com.example.moneyorders.api.jobs.repository.WithdrawRepository

@Service
class HandlerService @Autowired constructor(
        private val jobRepository: JobRepository,
        private val transactionService: TransactionService,
        private val depositJobRepository: DepositRepository,
        private val withdrawRepository: WithdrawRepository,
){
    fun process(id : Long){
        val data = jobRepository.findById(id)
        val type = data.get().type
        val job = data.get()

        when(type){
            "DEPOSIT"->{
                transactionService.processTransaction(job.transactionId)
                depositJobRepository.updateStatusByJobId(job.id, Status.SUCCESS.toString())
            }
            "WITHDRAW"->{
                transactionService.processTransaction(job.transactionId)
                withdrawRepository.updateStatusByJobId(job.id,Status.SUCCESS.toString())
            }
            "TRANSFER"->{
//                transactionService.processTransaction(job.transactionId)
//                transferRepository.updateStatusByJobId(job.id,Status.SUCCESS.toString())
            }
            else->{
                job.status = Status.FAILED
                jobRepository.save(job)
            }
        }
    }
}