package com.example.moneyorders.services

import com.example.moneyorders.interfaces.Job
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Service
class JobScheduler @Autowired constructor(
        private val jobService: JobService
) {

    private val scheduler: ExecutorService = Executors.newFixedThreadPool(3)
    private val jobQueue: Queue<Job> = LinkedList()
    @Scheduled(fixedRate = 10000)
    fun scheduler() {
        val noOfRequiredJobs = 10 - jobQueue.size
        val jobs = jobService.getJobsToExecute()
        for (job in jobQueue) {
            jobQueue.poll()
            scheduler.submit {
                job.execute()
            }
        }
    }
    fun scheduleJob(job : Job){
        try {
            jobQueue.offer(job)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}