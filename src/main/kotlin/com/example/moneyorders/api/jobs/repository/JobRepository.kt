package com.example.moneyorders.api.jobs.repository


import com.example.moneyorders.api.jobs.model.Job
import com.example.moneyorders.api.jobs.model.Status
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JobRepository : JpaRepository<Job, Long> {
    @Query("""
        SELECT j
        FROM Job j
        WHERE j.status = 'PENDING'
    """)
    fun findJobsToExecute(noOfJobs: Int,pageable: Pageable): List<Job>

    fun findAllByStatus(status: Status) : List<Job>


    override fun findById(id: Long): Optional<Job>


}