package com.example.moneyorders.api.jobs.repository


import com.example.moneyorders.api.jobs.model.Job
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JobRepository : JpaRepository<Job,Long>{
    @Query("""
        SELECT * FROM job WHERE status = 'PENDING' LIMIT :noOfJobs
    """, nativeQuery = true)
    fun findJobsToExecute(noOfJobs : Int): List<Job>

    override fun findById(id : Long) : Optional<Job>

}