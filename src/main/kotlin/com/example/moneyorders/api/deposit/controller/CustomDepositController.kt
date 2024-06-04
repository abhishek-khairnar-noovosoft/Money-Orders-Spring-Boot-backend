package com.example.moneyorders.api.deposit.controller

import com.example.moneyorders.api.deposit.services.CustomDepositService
import com.example.moneyorders.api.deposit.viewmodel.CustomDepositJobViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/deposit")
class CustomDepositController(
        val customDepositService: CustomDepositService
) {
    @PostMapping
    fun deposit(
            @RequestBody customDepositJobViewModel: CustomDepositJobViewModel
    ) : ResponseEntity<Any?>{
        customDepositService.createCustomDepositJob(customDepositJobViewModel)
        println("success")
        return ResponseEntity.ok().body(null)
    }
}