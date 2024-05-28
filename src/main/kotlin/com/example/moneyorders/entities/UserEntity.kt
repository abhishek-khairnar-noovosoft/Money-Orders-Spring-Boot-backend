package com.example.moneyorders.entities

import jakarta.persistence.*
import java.math.BigInteger

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @Column(name = "id")
        val id: Long = 0,
        @Column(name = "name")
        val name: String = "",
        @Column(name = "email")
        val email: String = "",
        @Column(name = "role")
        val role: String = "",
        @Column(name = "password")
        val password: String = "",
        @Column(name = "balance")
        var balance: BigInteger = BigInteger.ZERO
)