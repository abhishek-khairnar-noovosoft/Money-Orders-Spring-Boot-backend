package com.example.moneyorders.api.jobs.repository

import com.example.moneyorders.api.jobs.model.WithdrawJob
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface WithdrawRepository : JpaRepository<WithdrawJob,Long>{
    @Transactional
    @Modifying
    @Query(
            """
              UPDATE job SET status = :status WHERE id = :id  
            """
            , nativeQuery = true)
    fun updateStatusByJobId(id : Long,status : String)
}