package com.example.moneyorders

import com.example.moneyorders.services.JwtRequestFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class MoneyOrdersApplication

fun main(args: Array<String>) {
    runApplication<MoneyOrdersApplication>(*args)
}


@Configuration
@EnableWebSecurity
class SecurityConfig(
        private val jwtRequestFilter: JwtRequestFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/login", permitAll)
                authorize("/users", hasRole("manager"))
                authorize("/AllTransactions", hasRole("manager"))
                authorize("/transactions",hasRole("customer"))
                authorize("/profile",hasRole("customer"))
                authorize(anyRequest, authenticated)
            }
            formLogin { disable() }
            httpBasic { }
            csrf { disable() }
        }

        http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
