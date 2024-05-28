package com.example.moneyorders.repositories

import com.example.moneyorders.entities.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.http.ResponseEntity
import java.math.BigInteger

interface UserRepository : JpaRepository<UserEntity, Int> {
    @Query("""
        SELECT users.balance FROM users WHERE users.id=:id
    """, nativeQuery = true)
    fun getBalance(id: Long): BigInteger

    fun findById(id : Long) : UserEntity



    @Transactional
    @Modifying
    @Query("""
        UPDATE users u SET balance = :balance + :amount WHERE u.id = :id
    """, nativeQuery = true)
    fun deposit(balance : BigInteger,amount : BigInteger, id : Long)

    @Transactional
    @Modifying
    @Query("""
        UPDATE users u SET balance = :balance - :amount WHERE u.id = :id
    """, nativeQuery = true)
    fun withdraw(balance : BigInteger,amount : BigInteger, id : Long)


    fun findByEmail(email :String): UserEntity?



}