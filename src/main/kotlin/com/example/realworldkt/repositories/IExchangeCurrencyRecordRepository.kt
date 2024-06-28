package com.example.realworldkt.repositories

import com.example.realworldkt.entities.ExchangeCurrencyRecord
import org.springframework.data.jpa.repository.JpaRepository


interface IExchangeCurrencyRecordRepository : JpaRepository<ExchangeCurrencyRecord, Long> {
}
