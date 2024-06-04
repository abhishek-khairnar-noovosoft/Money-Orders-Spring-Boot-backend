package com.example.moneyorders.services

import com.example.moneyorders.api.jobs.model.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.moneyorders.api.jobs.repository.JobRepository

@Service
class HandlerService @Autowired constructor(
        private val jobRepository: JobRepository
){
    fun process(id : Long){
        var job = jobRepository.findById(id)
        job.get().status = Status.SUCCESS
        job.get().type


        jobRepository.save(job.get())
        println(jobRepository.findById(id))
    }
}