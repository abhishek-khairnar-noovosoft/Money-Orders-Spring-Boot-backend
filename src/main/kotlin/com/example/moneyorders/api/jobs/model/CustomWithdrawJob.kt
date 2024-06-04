package com.example.moneyorders.api.jobs.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.math.BigInteger

@Entity
@DiscriminatorValue("WITHDRAW")
class CustomWithdrawJob : Job() {
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    lateinit var customWithdrawJobData : CustomWithdrawJobData

    fun withData(customWithdrawJobData : CustomWithdrawJobData){
        this.type = JobType.WITHDRAW.toString()
        this.status = Status.PENDING
        this.customWithdrawJobData = customWithdrawJobData
    }

}

data class CustomWithdrawJobData(
        val withdrawFrom : Long,
        val transactionAmount : BigInteger
) : Serializable