//package com.example.realworldkt.services
//
//import com.example.realworldkt.enums.Currency
//import com.example.realworldkt.services.currency.interfaces.ICurrencyExchangeService
//import com.example.realworldkt.services.currency.interfaces.IExchangeRateProvider
//import kotlinx.coroutines.test.runTest
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.params.ParameterizedTest
//import org.junit.jupiter.params.provider.Arguments
//import org.junit.jupiter.params.provider.MethodSource
//import org.mockito.Mockito
//
//class CurrencyExchangeServiceImplTest {
//
//    private lateinit var exchangeRateProviderImpl: IExchangeRateProvider
//    private lateinit var currencyExchangeServiceImpl: ICurrencyExchangeService
//
//    companion object {
//        @JvmStatic
//        fun exchangeTestProvider() = listOf(
//            Arguments.of(100.0, Currency.USD, Currency.TWD, 30.0, 3000.0),
//            Arguments.of(100.0, Currency.TWD, Currency.USD, 1 / 30.0, 3.3333333333333335),
//            Arguments.of(100.0, Currency.TWD, Currency.TWD, 1.0, 100.0),
//            Arguments.of(100.0, Currency.TWD, Currency.JPY, 4.0, 400.0)
//        )
//    }
//
//    @BeforeEach
//    fun setUp() {
//        exchangeRateProviderImpl = Mockito.mock()
//        currencyExchangeServiceImpl = CurrencyExchangeServiceImpl(exchangeRateProviderImpl)
//    }
//
//    @ParameterizedTest(name = "convert {0} {1} to {2} with rate {3} should be {4} {2}")
//    @MethodSource("exchangeTestProvider")
//    fun `convert amount between currencies`(
//        amount: Double,
//        from: Currency,
//        to: Currency,
//        exchangeRate: Double,
//        expected: Double
//    ) = runTest {
//        Mockito.`when`(exchangeRateProviderImpl.getExchangeRate(from, to)).thenReturn(exchangeRate)
//        val result = currencyExchangeServiceImpl.convert(amount, from, to)
//        assertEquals(expected, result)
//    }
//}
