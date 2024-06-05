package com.example.moneyorders.api.jobs.queue

import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.repository.DepositRepository
import com.example.moneyorders.api.jobs.repository.JobRepository

abstract class QueueJob(
        open val jobRepository: JobRepository
) {
    private fun markJobAsInProcess(job : Job){
        jobRepository.save(job.apply { this.status = Status.PROCESSING })
    }

    private fun markJobAsFailed(job : Job, failureReason : String?){
        jobRepository.save(job.apply {
            this.status = Status.FAILED
            this.failureReason = failureReason
        })

    }

    abstract fun handle(job : Job)

    open fun execute(job : Job){
        try{
            markJobAsInProcess(job)

            handle(job)
        }
        catch (e : Exception){
            markJobAsFailed(job,e.message)
        }
    }

}