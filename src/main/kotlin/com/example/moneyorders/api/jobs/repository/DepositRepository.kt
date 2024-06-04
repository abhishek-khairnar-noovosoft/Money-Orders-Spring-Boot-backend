package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.DepositJob
import org.springframework.data.jpa.repository.JpaRepository

interface DepositRepository : JpaRepository<DepositJob,Long>