package com.example.moneyorders.services

import com.example.moneyorders.entities.Transaction
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Component
class TransactionScheduler(
        private val transactionService: TransactionService
) {
    private val numberOfThreads = 3
    private val transactionQueueCapacity = 10
    private val scheduler = Executors.newScheduledThreadPool(numberOfThreads)
    private val transactionQueue: BlockingQueue<Transaction> = ArrayBlockingQueue(transactionQueueCapacity)

    @PostConstruct
    fun startProcessing() {
        for (i in 0 until numberOfThreads) {
            scheduler.execute {
                while (true) {
                    try {
                        val transaction = transactionQueue.take()
                        transactionService.processTransaction(transaction)
                        println("processed transaction")
                    } catch (e: InterruptedException) {
                        Thread.currentThread().interrupt()
                        System.err.println("Interrupted while taking transaction from the queue: " + e.message)
                    }
                }
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    fun fetchAndQueueTransactions() {
        try {
            val latestTransactions: List<Transaction> = transactionService.getLatestProcessingTransactions().take(10)
            for (transaction in latestTransactions) {
                try {
                    transactionQueue.put(transaction)
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    System.err.println("Interrupted while adding transaction to the queue: " + e.message)
                }
            }
        } catch (e: Exception) {
            println("Something went wrong")
            e.printStackTrace()
        }
    }

    @PreDestroy
    fun shutDownScheduler() {
        scheduler.shutdown()
        try {
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduler.shutdownNow()
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Scheduler did not terminate")
                }
            }
        } catch (ie: InterruptedException) {
            scheduler.shutdownNow()
            Thread.currentThread().interrupt()
        }
    }
}
