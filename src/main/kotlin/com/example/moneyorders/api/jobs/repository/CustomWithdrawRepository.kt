package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.CustomWithdrawJob
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomWithdrawRepository : JpaRepository<CustomWithdrawJob,Long>