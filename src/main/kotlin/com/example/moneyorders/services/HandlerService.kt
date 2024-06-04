package com.example.moneyorders.services

import com.example.moneyorders.api.jobs.model.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.moneyorders.api.jobs.repository.JobRepository

@Service
class HandlerService @Autowired constructor(
        private val jobRepository: JobRepository,
        private val transactionService: TransactionService
){
    fun process(id : Long){
        val data = jobRepository.findById(id)
        val type = data.get().type
        val job = data.get()

        when(type){
            "DEPOSIT"->{
                println("DEPOSIT")
//                job.status = Status.PROCESSING
//                jobRepository.save(job)
//                transactionService.processTransaction(id)
//                job.status = Status.SUCCESS
//                jobRepository.save(job)
            }
            "WITHDRAW"->{
                println("WITHDRAW")
//                job.status = Status.PROCESSING
//                jobRepository.save(job)
//                transactionService.processTransaction(id)
//                job.status = Status.SUCCESS
//                jobRepository.save(job)
            }
            "TRANSFER"->{
                println("TRANSFER")
//                job.status = Status.PROCESSING
//                jobRepository.save(job)
//                transactionService.processTransaction(id)
//                job.status = Status.SUCCESS
//                jobRepository.save(job)
            }
            else->{
//                job.status = Status.FAILED
//                jobRepository.save(job)
            }
        }
    }
}