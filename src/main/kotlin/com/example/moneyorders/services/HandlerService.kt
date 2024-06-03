package com.example.moneyorders.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.moneyorders.repositories.JobRepository

@Service
class HandlerService @Autowired constructor(
        private val jobRepository: JobRepository
){
    fun process(id : Long){
        var job = jobRepository.findById(id)
        job.get().status = "completed"
        jobRepository.save(job.get())
        println(jobRepository.findById(id))
    }
}