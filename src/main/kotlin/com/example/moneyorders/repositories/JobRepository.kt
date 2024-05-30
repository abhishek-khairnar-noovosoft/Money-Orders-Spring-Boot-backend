package com.example.moneyorders.repositories

import com.example.moneyorders.entities.Job as JobEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobRepository : JpaRepository<JobEntity,Long>