package com.example.moneyorders.api.jobs.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime


enum class JobType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    PRINT
}

enum class Status {
    PENDING,
    FAILED,
    PROCESSING,
    SUSPENDED,
    SUCCESS
}
data class JobDataViewModel(
        val jobTypeId: Long,
        val jobType : JobType,
        val status : Status
)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( discriminatorType = DiscriminatorType.STRING)
abstract class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id : Long = 0

    @Column(columnDefinition = "varchar(255) not null")
    open var type : String = ""

    @Column(length = 11, columnDefinition = "varchar(11) default 'PENDING'")
    @Enumerated(value = EnumType.STRING)
    open var status: Status = Status.PENDING

    @CreationTimestamp
    @Column( nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    open var createdAt : LocalDateTime = LocalDateTime.now()

    @Column( nullable = true, columnDefinition = "text")
    open var failureReason : String? = null

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(insertable=false, updatable=false,nullable = false, columnDefinition = "jsonb")
    open var data : Any? = null
}