package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.WithdrawJob
import org.springframework.data.jpa.repository.JpaRepository

interface WithdrawRepository : JpaRepository<WithdrawJob, Long>