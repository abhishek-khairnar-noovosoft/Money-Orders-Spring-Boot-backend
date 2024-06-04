package com.example.moneyorders.api.jobs.service

import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService @Autowired constructor(
        private val jobRepository: JobRepository
) {

    fun getJobsToExecute(noOfJobsRequired : Int) : List<Job> {
        return jobRepository.findJobsToExecute(noOfJobsRequired)
    }

}