package com.example.moneyorders.api.withdraw.controller

import com.example.moneyorders.api.withdraw.service.CustomWithdrawService
import com.example.moneyorders.api.withdraw.viewmodel.CustomWithdrawViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/withdraw")
class CustomWithdrawController(
        val customWithdrawService: CustomWithdrawService
) {
    @PostMapping
    fun withdraw(
            @RequestBody customWithdrawViewModel: CustomWithdrawViewModel
    ): ResponseEntity<Any?>{
        customWithdrawService.createCustomWithdrawJob(customWithdrawViewModel)
        return ResponseEntity.ok().body("withdraw successful!!")
    }

}