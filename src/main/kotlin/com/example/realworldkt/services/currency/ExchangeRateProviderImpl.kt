package com.example.realworldkt.services.currency

import com.example.realworldkt.enums.Currency
import com.example.realworldkt.services.currency.interfaces.IExchangeRateProvider
import org.springframework.stereotype.Service

@Service
class ExchangeRateProviderImpl : IExchangeRateProvider {
    private val amountsRequiredToUSD: Map<Currency, Double> = mapOf(
        Currency.USD to 1.0,
        Currency.TWD to 30.0,
        Currency.JPY to 110.0
    )

    override suspend fun getExchangeRate(from: Currency, to: Currency): Double {
        val fromUsdUnit = amountsRequiredToUSD[from] ?: error("Unknown currency $from")
        val toUsdUnit = amountsRequiredToUSD[to] ?: error("Unknown currency $to")
        return toUsdUnit / fromUsdUnit
    }
}
