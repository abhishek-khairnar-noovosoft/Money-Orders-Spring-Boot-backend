package com.example.moneyorders.api.jobs.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.math.BigInteger

@Entity
@DiscriminatorValue("TRANSFER")
class TransferJob : Job() {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    lateinit var transferJobData : TransferJobData

    fun withData(transferJobData: TransferJobData){
        this.type = JobType.TRANSFER.toString()
        this.status = Status.PENDING
        this.transferJobData = transferJobData
    }
}

data class TransferJobData(
        val withdrawFrom : Long,
        val depositTo : Long,
        val transactionAmount : BigInteger
)