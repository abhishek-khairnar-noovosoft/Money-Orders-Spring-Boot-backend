package com.example.moneyorders.filters


import com.example.moneyorders.services.JwtService
import com.example.moneyorders.services.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(
        private val userDetailsService: UserService,
        private val jwtUtil: JwtService,
        private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain,
    ) {
        val requestTokenHeader = request.getHeader("Authorization")
        if (!request.requestURL.endsWith("/login")) {
            if (requestTokenHeader == null) {
                val errorMessage = "Authorization token missing or invalid"
                val responseBody = mapOf("error" to errorMessage)
                val responseJson = objectMapper.writeValueAsString(responseBody)
                val responseEntity = ResponseEntity(responseJson, HttpStatus.UNAUTHORIZED)
                response.contentType = "application/json"
                response.writer.write(responseEntity.body.toString())
                response.status = 401
                return
            }
        }

        if (requestTokenHeader != null) {
            val jwtToken = requestTokenHeader.substring(7)
            val email = jwtUtil.getUsernameFromToken(jwtToken)
            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(email)
                if (jwtUtil.validateToken(jwtToken)) {
                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                    )
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }
        filterChain.doFilter(request, response)

    }
}