package com.example.realworldkt.services.currency.interfaces

import com.example.realworldkt.enums.Currency

interface ICurrencyExchangeService {
    suspend fun convert(amount: Double, from: Currency, to: Currency): Double
}
