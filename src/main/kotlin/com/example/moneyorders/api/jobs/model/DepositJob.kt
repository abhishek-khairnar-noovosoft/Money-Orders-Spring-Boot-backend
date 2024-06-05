package com.example.moneyorders.api.jobs.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.math.BigInteger

@Entity
@DiscriminatorValue("DEPOSIT")
class DepositJob : Job() {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    lateinit var customDepositJobData : CustomDepositJobData

    fun withData(customDepositJobData: CustomDepositJobData){
        this.type = JobType.DEPOSIT.toString()
        this.status = Status.PENDING
        this.customDepositJobData = customDepositJobData
    }
}

data class CustomDepositJobData(
        val depositTo : Long,
        val transactionAmount : BigInteger
)