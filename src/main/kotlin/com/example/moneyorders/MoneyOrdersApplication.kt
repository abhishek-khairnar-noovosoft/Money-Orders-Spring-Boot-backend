package com.example.moneyorders

import com.example.moneyorders.services.UserEntityDetailService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
class MoneyOrdersApplication

fun main(args: Array<String>) {
    runApplication<MoneyOrdersApplication>(*args)
}


@Configuration
@EnableWebSecurity
class SecurityConfig(private val userDetailsService: UserEntityDetailService) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/login", permitAll)
                authorize("/users", hasRole("manager"))
                authorize("/AllTransactions", hasRole("manager"))
                authorize("/transactions",hasRole("customer"))
                authorize(anyRequest, authenticated)
            }
            formLogin { }
            httpBasic { }
        }

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        return userDetailsService
    }

    @Bean
    fun AuthenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
