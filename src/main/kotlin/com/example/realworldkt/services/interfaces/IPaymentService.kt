package com.example.realworldkt.services.interfaces

interface IPaymentService {
    suspend fun pay(): Boolean
    suspend fun refund(): Boolean
}
