package com.example.realworldkt.services.currency

import com.example.realworldkt.enums.Currency
import com.example.realworldkt.services.currency.interfaces.IExchangeRateProvider

import org.springframework.stereotype.Service


@Service
class ExchangeRateProviderByApi(
    private val exchangeRateApiService: ExchangeRateApiService
) : IExchangeRateProvider {
    override suspend fun getExchangeRate(from: Currency, to: Currency): Double {
        val conversionRate = exchangeRateApiService.fetchExchangeRates(from)
        return conversionRate?.get(to.name)?.toString()?.toDouble() ?: 0.0
    }
}
