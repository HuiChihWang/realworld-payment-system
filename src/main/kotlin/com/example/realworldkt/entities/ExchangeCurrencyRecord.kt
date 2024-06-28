package com.example.realworldkt.entities

import com.example.realworldkt.enums.Currency
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime


@Entity(name = "exchange_currency_records")
data class ExchangeCurrencyRecord(
    @Id
    @Column(name = "record_oid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "record_seq",
        sequenceName = "record_seq",
        allocationSize = 2
    )
    val recordOid: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "from_currency")
    val fromCurrency: Currency,

    @Enumerated(EnumType.STRING)
    @Column(name = "to_currency")
    val toCurrency: Currency,

    @Column(name = "exchange_rate")
    val exchangeRate: Double,

    @Column(name = "amount")
    val amount: Double,

    @Column(name = "converted_amount")
    val convertedAmount: Double,

    @CreatedDate
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @CreatedBy
    @Column(name = "created_by")
    val createdBy: String = "system"
)
