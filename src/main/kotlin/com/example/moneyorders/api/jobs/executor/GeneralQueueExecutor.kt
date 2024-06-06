package com.example.moneyorders.api.jobs.executor

import com.example.moneyorders.api.jobs.model.JobType
import com.example.moneyorders.api.jobs.model.Status
import com.example.moneyorders.api.jobs.queue.DepositQueue
import com.example.moneyorders.api.jobs.queue.TransferQueue
import com.example.moneyorders.api.jobs.queue.WithdrawQueue
import com.example.moneyorders.api.jobs.repository.JobRepository
import com.example.moneyorders.api.jobs.viewmodel.JobWithType
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Component
class GeneralQueueExecutor(
        val jobRepository: JobRepository,
        val depositQueue: DepositQueue,
        val withdrawQueue : WithdrawQueue,
        val transferQueue : TransferQueue,
        @Value("3")
        private val noOfThreads: Int,
) {
    private val scheduledExecutor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private val executor = Executors.newFixedThreadPool(noOfThreads)
    private val queue: Queue<JobWithType> = LinkedList()

    @PreDestroy
    fun destroy() {
        scheduledExecutor.shutdownNow()
        executor.shutdown()
        executor.awaitTermination(60, TimeUnit.SECONDS)
    }

    @Scheduled(fixedRate = 10000)
    fun executeJobs() {
        scheduledExecutor.scheduleWithFixedDelay(
                { fetchJobs() }, 0, 10, TimeUnit.SECONDS
        )
    }

    fun fetchJobs() {
        if (queue.isEmpty()) {
            try{
                fill()
            }catch (e : Exception){
                println("something went wrong")
            }
        }
        while (queue.isNotEmpty()) {
            val job = queue.poll()
            execute(job)
        }
    }

    private fun fill() {
        val noOfJobsRequired = 10 - queue.size
        val jobs = jobRepository.findJobsToExecute(noOfJobsRequired, Pageable.ofSize(noOfJobsRequired))
        queue.addAll(
                jobs.map {
                    JobWithType(
                            id = it.id,
                            type = it.type,
                            job = it
                    )
                }
        )
    }

    private fun execute(jobWithType: JobWithType?) {
        if (jobWithType == null) {
            return
        }

        executor.submit {
            when (jobWithType.job.type) {
                JobType.DEPOSIT.toString() -> depositQueue.execute(jobWithType.job)
                JobType.WITHDRAW.toString() -> withdrawQueue.execute(jobWithType.job)
                JobType.TRANSFER.toString() -> transferQueue.execute(jobWithType.job)
            }
        }
    }
}