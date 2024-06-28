package com.example.realworldkt.services.currency.interfaces

import com.example.realworldkt.enums.Currency

interface IExchangeRateProvider {
    suspend fun getExchangeRate(from: Currency, to: Currency): Double
}
