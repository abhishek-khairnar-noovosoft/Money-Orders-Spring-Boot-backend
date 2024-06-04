package com.example.moneyorders.api.deposit.services

import com.example.moneyorders.api.deposit.viewmodel.CustomDepositJobViewModel
import com.example.moneyorders.api.jobs.model.CustomDepositJob
import com.example.moneyorders.api.jobs.model.CustomDepositJobData
import com.example.moneyorders.api.jobs.repository.CustomDepositJobRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CustomDepositService(
        val customDepositRepository : CustomDepositJobRepository
) {
    @Transactional
    fun createCustomDepositJob(
            customDepositViewModel : CustomDepositJobViewModel
    ){
        val customDepositJob = CustomDepositJob()

        customDepositJob.withData(
                CustomDepositJobData(
                        depositTo = customDepositViewModel.depositTo,
                        transactionAmount = customDepositViewModel.transactionAmount
                )
        )
        customDepositRepository.save(customDepositJob)
    }

}