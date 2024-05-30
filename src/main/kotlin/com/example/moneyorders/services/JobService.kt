package com.example.moneyorders.services

import com.example.moneyorders.entities.Job
import com.example.moneyorders.repositories.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService @Autowired constructor(
        private val jobRepository: JobRepository
) {
    fun getJobsToExecute() : List<Job> {
        return jobRepository.findAll()
    }

}