package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.WithdrawService
import com.example.moneyorders.api.transactions.viewmodel.WithdrawJobViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/withdraw")
class WithdrawController(
        val withdrawService: WithdrawService
) {
    @PostMapping
    fun withdraw(
            @RequestBody withdrawJobViewModel: WithdrawJobViewModel
    ) : ResponseEntity<Any?>{
        withdrawService.createWithdrawJob(withdrawJobViewModel)
        return ResponseEntity.ok().body("withdraw successful!!")
    }
}