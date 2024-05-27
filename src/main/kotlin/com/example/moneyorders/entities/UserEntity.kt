package com.example.moneyorders.entities

import jakarta.persistence.*
import java.math.BigInteger

@Entity
@Table(name = "users")
class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,
        val name: String,
        val email: String,
        val role: String,
        val password: String,
        val balance: BigInteger

) {
    constructor() : this(0,"","","","",BigInteger.ZERO)
}