package com.example.moneyorders.entities

import com.example.moneyorders.services.MapToJsonConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serial

@Entity
class JobEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Serial
    val id : Long = 0,
    @Column(name = "name")
    var name : String = "",
    @Column(name = "job_type")
    var jobType : String = "",
    @Column(name = "status")
    var status : String = "processing",
    @Convert(converter = MapToJsonConverter::class)
    @Column(name="parameters",columnDefinition = "jsonb", nullable = true)
    val parameters :Map<String,String> = mutableMapOf()
)