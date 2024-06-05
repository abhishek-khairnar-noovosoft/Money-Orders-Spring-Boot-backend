package com.example.moneyorders.api.transactions.controller

import com.example.moneyorders.api.transactions.services.TransferService
import com.example.moneyorders.api.transactions.viewmodel.TransferJobViewModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/transfer")
class TransferController(
        val transferService: TransferService
) {
    @PostMapping
    fun transfer(
            @RequestBody transferJobViewModel: TransferJobViewModel
    ) : ResponseEntity<Any?>{
        transferService.createCustomTransferJob(transferJobViewModel)
        return ResponseEntity.ok().body("transfer successful!!")
    }
}