package com.example.moneyorders.api.jobs.repository


import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JobRepository : JpaRepository<Job,Long>{
    @Query("""
        SELECT * FROM job WHERE status = 'PENDING' OR status = 'PROCESSING' LIMIT :noOfJobs
    """, nativeQuery = true)
    fun findJobsToExecute(noOfJobs : Int): List<Job>

    override fun findById(id : Long) : Optional<Job>

    @Query("""
        SELECT * FROM job WHERE status = :status AND type = :jobType LIMIT :limit
    """, nativeQuery = true)
    fun findAllPendingJobs(limit : Int,status : String,jobType : String) : List<Job>

    fun findFirst10ByStatusAndType(status: Status, type: String) : List<Job>


}