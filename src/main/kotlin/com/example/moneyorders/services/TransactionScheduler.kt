package com.example.moneyorders.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Service
class TransactionScheduler @Autowired constructor(
        private val transactionService: TransactionService,
) {

    val workQueue : Queue<Long> = LinkedList()
    val threadPool : ExecutorService = Executors.newFixedThreadPool(3)
    fun fillWorkQueue(transactions: Queue<Long>){
        for(id in transactions){
            if(workQueue.size < 10){
                workQueue.offer(id)
            }else{
                break
            }
        }
        println("workQueue $workQueue")
        return
    }

    @Scheduled(fixedRate = 10000)
    fun scheduler(){
        val noOfRequiredTransactions = 10 - workQueue.size
        val transactions = transactionService.getLatestProcessingTransactions(noOfRequiredTransactions)
        val inputQueue : Queue<Long> = LinkedList()

        for(transaction in transactions){
            inputQueue.offer(transaction.id)
        }

        fillWorkQueue(inputQueue)

        for(id in workQueue){
            workQueue.poll()
            threadPool.submit {
                transactionService.processTransaction(id)
            }
        }
    }


}