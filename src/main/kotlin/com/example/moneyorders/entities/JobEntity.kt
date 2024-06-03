package com.example.moneyorders.entities

import com.example.moneyorders.models.TransactionsViewModel
import jakarta.persistence.*
import java.math.BigInteger

@Entity

data class JobEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(name = "name")
        var name: String = "",

        @Column(name = "job_type")
        var jobType: String = "",

        @Column(name = "status")
        var status: String = "processing",
//
//        @Column(name = "parameters", columnDefinition = "jsonb")
//        var parameters: ViewModel
)

@Table(name = "job_entity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "parameters")
data class ViewModel(
        val name : String
)
@DiscriminatorValue("deposit")
data class DepositViewModel2(
        val name : String,
        val amount : BigInteger
)


// map should be readonly
// discriminator value
// discriminator column
// inheritance