package com.example.moneyorders.controllers

import com.example.moneyorders.entities.UserEntity
import com.example.moneyorders.models.UserModel
import com.example.moneyorders.services.JwtService
import com.example.moneyorders.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class AuthController @Autowired constructor(
        private val userService: UserService,
        private val jwtService : JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody userModel: UserModel) : ResponseEntity<UserEntity> {
        val user = userService.getUserByEmail(userModel.email) ?: throw IllegalArgumentException("login failed")
        if (!BCrypt.checkpw(userModel.password,user.password)) {
            throw IllegalArgumentException("invalid credentials")
        }
        val jwtToken = jwtService.generateJwtToken(user)
        val headers = HttpHeaders()
        headers.set("Authorization",jwtToken)
        return ResponseEntity.ok().headers(headers).body(user)
    }


    @DeleteMapping("/logout")
    fun logout(){}







}

// HttpFilters
