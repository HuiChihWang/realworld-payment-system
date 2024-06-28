package com.example.realworldkt.controllers

import com.example.realworldkt.dto.request.CurrencyExchangeQuery
import com.example.realworldkt.services.currency.interfaces.ICurrencyExchangeService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/currency-exchange")
class CurrencyExchangeController(
    private val currencyExchangeService: ICurrencyExchangeService
) {
    @GetMapping
    suspend fun getCurrencyExchange(
         @Valid @ModelAttribute params: CurrencyExchangeQuery
    ): ResponseEntity<Double> {
        val (from, to, amount) = params
        val convertedAmount = currencyExchangeService.convert(amount, from, to)
        return ResponseEntity.ok(convertedAmount)
    }
}
