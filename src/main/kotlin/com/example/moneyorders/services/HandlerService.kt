package com.example.moneyorders.services

import com.example.moneyorders.entities.JobEntity
import com.example.moneyorders.interfaces.JobHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import com.example.moneyorders.interfaces.PrintJobHandler

@Service
class HandlerService @Autowired constructor(
        private val printJobHandler: PrintJobHandler,
        private val transactionService: TransactionService
){

    private val handlers: Map<String, JobHandler> = mapOf(
            "PRINT" to printJobHandler
    )

    fun getHandler(jobType: String): JobHandler? {
        return handlers[jobType]
    }


}