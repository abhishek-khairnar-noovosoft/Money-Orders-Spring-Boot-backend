package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.DepositJob
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface DepositRepository : JpaRepository<DepositJob,Long>