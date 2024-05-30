package com.example.moneyorders.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
abstract class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id : Long = 0
    @Column(name = "name")
    val name : String = ""
    @Column(name = "jobType")
    val jobType : String = ""
    @Column(name = "status")
    val status : String = "processing"
    @get:Column(columnDefinition = "jsonb")
    abstract val parameters : Map<String,String>
}