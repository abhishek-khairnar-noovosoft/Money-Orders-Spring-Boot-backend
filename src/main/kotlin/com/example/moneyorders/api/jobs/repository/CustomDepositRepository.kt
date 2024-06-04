package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.CustomDepositJob
import org.springframework.data.jpa.repository.JpaRepository

interface CustomDepositRepository : JpaRepository<CustomDepositJob,Long>