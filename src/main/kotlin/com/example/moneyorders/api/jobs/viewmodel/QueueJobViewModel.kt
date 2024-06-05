package com.example.moneyorders.api.jobs.viewmodel

import com.example.moneyorders.api.jobs.model.Job

data class JobWithType(
        val id : Long,
        val type : String,
        val job : Job
)