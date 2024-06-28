package com.example.realworldkt.dto.request
import com.example.realworldkt.enums.Currency
import jakarta.validation.constraints.*

data class CurrencyExchangeQuery(
    val from: Currency,

    val to: Currency,

    @field:Positive
    val amount: Double
)
