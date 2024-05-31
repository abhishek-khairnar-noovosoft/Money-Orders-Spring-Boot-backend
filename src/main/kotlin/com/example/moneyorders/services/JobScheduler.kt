package com.example.moneyorders.services

import com.example.moneyorders.entities.JobEntity as Job
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Service
class JobScheduler @Autowired constructor(
        private val jobService: JobService,
        private val handlerService: HandlerService
) {
    private val scheduler: ExecutorService = Executors.newFixedThreadPool(3)
    private val jobQueue: Queue<Job> = LinkedList()
    fun fillJobQueue(inputQueue: Queue<Long>) {
        for (job in inputQueue) {
            if (jobQueue.size < 10) {
                val jobEntity = jobService.getJob(job)
                jobQueue.offer(jobEntity)
            } else {
                break
            }
        }
        println("jobQueue $jobQueue")
        return
    }

    @Scheduled(fixedRate = 10000)
    fun scheduler() {
        val noOfRequiredJobs = 10 - jobQueue.size
        val jobs = jobService.getJobsToExecute(noOfRequiredJobs)
        val inputQueue: Queue<Long> = LinkedList()
        for (job in jobs) {
            inputQueue.offer(job.id)
        }
        fillJobQueue(inputQueue)
        while (jobQueue.isNotEmpty()) {
            val job = jobQueue.poll()
            val handler = handlerService.getHandler(job.jobType)
            scheduler.submit {
                handler?.execute(job)
                job.status = "completed"
                jobService.saveJob(job)
            }
        }
    }

    fun scheduleJob(job: Job) {
        try {
            jobQueue.offer(job)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}