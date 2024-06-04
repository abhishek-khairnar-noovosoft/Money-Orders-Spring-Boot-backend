package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.TransferService
import com.example.moneyorders.api.transactions.viewmodel.TransferViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/deposit")
class TransferController(
        val transferService: TransferService
) {
    @PostMapping("/transfer")
    fun transfer(
            @RequestBody transferViewModel: TransferViewModel
    ) : ResponseEntity<Any?>{
        transferService.createTransferJob(transferViewModel)
        return ResponseEntity.ok().body("transfer successful!!")
    }
}