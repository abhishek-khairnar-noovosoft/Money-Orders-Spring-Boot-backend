package com.example.moneyorders.repositories

import com.example.moneyorders.entities.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.math.BigInteger

interface UserRepository : JpaRepository<UserEntity, Int> {
    fun findById(id : Long) : UserEntity
    fun findByEmail(email :String): UserEntity?




}