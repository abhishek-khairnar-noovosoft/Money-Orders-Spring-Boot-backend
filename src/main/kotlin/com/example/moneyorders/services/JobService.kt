package com.example.moneyorders.services

import com.example.moneyorders.entities.JobEntity
import com.example.moneyorders.repositories.JobRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobService @Autowired constructor(
        private val jobRepository: JobRepository
) {
    private val objectMapper = jacksonObjectMapper()
    fun getJobsToExecute(noOfJobsRequired : Int) : List<JobEntity> {
        return jobRepository.findJobsToExecute(noOfJobsRequired)
    }

    fun getJob(id : Long):JobEntity{
        return jobRepository.getReferenceById(id)
    }
    fun updateStatus(job : JobEntity){
        jobRepository.save(job)
    }

    fun saveJob(job : JobEntity) {
//        val parametersJson = objectMapper.writeValueAsString(job.parameters)
        jobRepository.save(job)
    }



}