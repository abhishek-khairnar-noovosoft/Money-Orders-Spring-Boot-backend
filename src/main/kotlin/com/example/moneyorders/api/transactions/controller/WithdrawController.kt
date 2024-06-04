package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.WithdrawService
import com.example.moneyorders.api.transactions.viewmodel.WithdrawViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/withdraw")
class WithdrawController(
        private val withdrawService: WithdrawService
) {

    @PostMapping
    fun withdraw(
            @RequestBody withdrawViewModel : WithdrawViewModel
    ) : ResponseEntity<Any?>{
        withdrawService.createWithdrawJob(withdrawViewModel)
        return ResponseEntity.ok().body("withdraw successful!!")
    }

}