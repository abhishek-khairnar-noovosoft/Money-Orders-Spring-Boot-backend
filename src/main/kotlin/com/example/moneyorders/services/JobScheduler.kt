package com.example.moneyorders.services

import com.example.moneyorders.repositories.JobRepository
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
        println("jobQueue $jobQueue")
        val noOfRequiredJobs = 10 - jobQueue.size
        val jobs = jobService.getJobsToExecute(noOfRequiredJobs)

        for (job in jobs)
            jobQueue.offer(job.id)

        while (jobQueue.isNotEmpty()) {
            val id = jobQueue.poll()
            scheduler.submit {
                handlerService.process(id)
            }
        }
    }
}