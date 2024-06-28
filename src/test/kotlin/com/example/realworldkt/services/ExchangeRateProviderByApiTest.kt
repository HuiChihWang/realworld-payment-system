package com.example.realworldkt.services

import com.example.realworldkt.enums.Currency
import com.example.realworldkt.services.currency.ExchangeRateApiService
import com.example.realworldkt.services.currency.ExchangeRateProviderByApi
import com.example.realworldkt.services.currency.interfaces.IExchangeRateProvider
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ExchangeRateProviderByApiTest {
    private lateinit var provider: IExchangeRateProvider
    private lateinit var mockedExchangeApiService: ExchangeRateApiService

    @BeforeEach
    fun setUp() {
        mockedExchangeApiService = mock()
        provider = ExchangeRateProviderByApi(
            exchangeRateApiService = mockedExchangeApiService
        )
    }

    @Test
    fun `should get correct exchange rate`() = runTest {
        val from = Currency.USD
        val to = Currency.TWD
        val expected = 30.0
        val testConversionRates = mapOf(
            "TWD" to 30.0
        )

        whenever(mockedExchangeApiService.fetchExchangeRates(from)).thenReturn(testConversionRates)

        val result = provider.getExchangeRate(from, to)
        assert(result == expected)
    }

    @Test
    fun `should return 0 when conversion rate is not found`() = runTest {
        val from = Currency.USD
        val to = Currency.TWD
        val testConversionRates = mapOf(
            "JPY" to 30.0
        )
        val result = provider.getExchangeRate(from, to)
        assert(result == 0.0)
    }

}
