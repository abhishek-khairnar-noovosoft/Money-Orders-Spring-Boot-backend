package com.example.moneyorders.services

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
@Component
class JwtRequestFilter (
        private val userDetailsService : UserService,
        private val jwtUtil:JwtService
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("Executing")
        val requestTokenHeader = request.getHeader("Authorization")
        var email:String? = null
        var jwtToken : String? = null
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7)
            try{
                email = jwtUtil.getUsernameFromToken(jwtToken)
            }catch (e : IllegalArgumentException){
                println("Unable to get JWT Token")
            } catch (e : ExpiredJwtException){
                println("JWT Token has expired")
            }
        } else {
            println("JWT Token does not begin with Bearer")
        }
        if(email != null && SecurityContextHolder.getContext().authentication == null){
            val userDetails = userDetailsService.loadUserByUsername(email)
            if(jwtUtil.validateToken(jwtToken!!)){
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request,response)
    }
}