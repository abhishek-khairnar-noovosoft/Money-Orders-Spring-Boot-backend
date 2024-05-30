package com.example.moneyorders.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(username: String): String {
        val claims = Jwts.claims().setSubject(username)
        val now = Date()
        val expiryDate = Date(now.time + 1000 * 60 * 60 * 10) // 10 hours

        return "Bearer "+Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    fun validateToken(token: String): Boolean {
        val claims = getClaimsFromToken(token)
        return !claims.expiration.before(Date())
    }

    fun getUsernameFromToken(token: String): String {
        val claims = getClaimsFromToken(token)
        return claims.subject
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
    }
}
