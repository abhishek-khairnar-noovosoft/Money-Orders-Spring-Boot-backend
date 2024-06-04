package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.DepositService
import com.example.moneyorders.api.transactions.viewmodel.DepositJobViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/deposit")
class DepositController(
        val depositService: DepositService
) {
    @PostMapping
    fun deposit(
            @RequestBody depositJobViewModel: DepositJobViewModel
    ) : ResponseEntity<Any?>{
        depositService.createCustomDepositJob(depositJobViewModel)
        return ResponseEntity.ok().body("deposit successful!!")
    }
}