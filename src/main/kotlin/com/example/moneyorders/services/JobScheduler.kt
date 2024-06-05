package com.example.moneyorders.services

import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.service.JobService
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
    private val jobQueue: Queue<Long> = LinkedList()

    @Scheduled(fixedRate = 10000)
    fun scheduler() {
        val noOfRequiredJobs = 10 - jobQueue.size
        val jobs = jobService.getJobsToExecute(noOfRequiredJobs)


        for (job in jobs) {
            jobQueue.offer(job.id)
        }

        println("jobQueue $jobQueue")
        while (jobQueue.isNotEmpty()) {
            val id = jobQueue.poll()
            scheduler.submit {
                println("working till now")
                handlerService.process(id)
            }
        }
    }
}