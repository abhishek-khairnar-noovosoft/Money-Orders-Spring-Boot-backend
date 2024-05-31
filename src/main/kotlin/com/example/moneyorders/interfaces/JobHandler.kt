package com.example.moneyorders.interfaces

import com.example.moneyorders.entities.JobEntity
import org.springframework.stereotype.Component

interface JobHandler {
    fun execute(job: JobEntity)
}

@Component
class PrintJobHandler() : JobHandler {
    override fun execute(job: JobEntity) {
        println("Handling print job: ${job.name}")
    }
}