package com.example.realworldkt.services.currency

import com.example.realworldkt.entities.ExchangeCurrencyRecord
import com.example.realworldkt.enums.Currency
import com.example.realworldkt.repositories.IExchangeCurrencyRecordRepository
import com.example.realworldkt.services.currency.interfaces.ICurrencyExchangeService
import com.example.realworldkt.services.currency.interfaces.IExchangeRateProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class CurrencyExchangeServiceImpl(
    @Qualifier("exchangeRateProviderByApi") private val exchangeRateProvider: IExchangeRateProvider,
    private val exchangeCurrencyRecordRepository: IExchangeCurrencyRecordRepository
) : ICurrencyExchangeService {
    override suspend fun convert(amount: Double, from: Currency, to: Currency): Double {
        val exchangeRate = exchangeRateProvider.getExchangeRate(from, to)
        val convertedAmount  = amount * exchangeRate

        val record = ExchangeCurrencyRecord(
            fromCurrency = from,
            toCurrency = to,
            amount = amount,
            exchangeRate = exchangeRate,
            convertedAmount = convertedAmount
        )
        exchangeCurrencyRecordRepository.save(record)

        return convertedAmount
    }
}
