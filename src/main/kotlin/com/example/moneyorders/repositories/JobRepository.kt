package com.example.moneyorders.repositories

import jakarta.transaction.Transactional

import com.example.moneyorders.entities.JobEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JobRepository : JpaRepository<JobEntity,Long>{
    @Query("""
        SELECT * FROM job_entity WHERE status = 'processing' LIMIT :noOfJobs
    """, nativeQuery = true)
    fun findJobsToExecute(noOfJobs : Int): List<JobEntity>

    @Modifying
    @Transactional
    @Query(
            """
        INSERT INTO job_entity (name, job_type, status, parameters) 
        VALUES (:name, :jobType, :status, CAST(:parameters AS jsonb))
        """,
            nativeQuery = true
    )
    fun insertJob(name: String, jobType: String, status: String, parameters: String)

}