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
        val job = jobRepository.findById(id)
        val type = job.get().type

        when(type){
            "DEPOSIT"->{
                job.get().status = Status.PROCESSING
                jobRepository.save(job.get())
                transactionService.depositProcessing(id)
                job.get().status = Status.SUCCESS
                jobRepository.save(job.get())
            }
            "WITHDRAW"->{
                println("withdraw")
            }
            "TRANSFER"->{
                println("transfer")
            }
        }


        jobRepository.save(job.get())
        println(jobRepository.findById(id))
    }
}