package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.TransferJob
import org.springframework.data.jpa.repository.JpaRepository

interface TransferRepository : JpaRepository<TransferJob,Long>