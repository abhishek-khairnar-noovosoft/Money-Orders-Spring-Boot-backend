package com.example.moneyorders.services

import com.example.moneyorders.entities.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.SHA256Digest
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val jwtExpirationMs = (1000 * 60) * 60
    fun generateJwtToken(userEntity: UserEntity) : String{
        val token = Jwts.builder()
                .setSubject(userEntity.email)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + jwtExpirationMs))
                .signWith(key)
        return "Bearer $token"
    }
}