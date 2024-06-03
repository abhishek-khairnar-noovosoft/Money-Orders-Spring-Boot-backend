package com.example.moneyorders.services

import com.example.moneyorders.entities.JobEntity
import com.example.moneyorders.repositories.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService @Autowired constructor(
        private val jobRepository: JobRepository
) {

    fun sayHello(){
        println("hello i am here")
    }
    fun getJobsToExecute(noOfJobsRequired : Int) : List<JobEntity> {
        return jobRepository.findJobsToExecute(noOfJobsRequired)
    }

    fun getJob(id : Long):JobEntity{
        return jobRepository.getReferenceById(id)
    }
}