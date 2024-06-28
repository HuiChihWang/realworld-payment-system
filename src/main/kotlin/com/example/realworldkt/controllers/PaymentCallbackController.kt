package com.example.realworldkt.controllers

import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.LinePayConfirmQuery
import com.example.realworldkt.dto.request.PaymentCallBackRequest
import com.example.realworldkt.enums.Currency
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

//TODO: should implement this
@RestController
@RequestMapping("/api/payment/callback")
class PaymentCallbackController(
    @Qualifier("linePayGatewayService") private val linePayService: IPaymentGatewayService
) {
    private val logger = LoggerFactory.getLogger(PaymentCallbackController::class.java)

    @GetMapping("/linepay")
    suspend fun confirm(@ModelAttribute params: LinePayConfirmQuery): ResponseEntity<PaymentResult> {
        // design data adapter to adapt callback data to common format
        val ret = linePayService.afterPay(
            PaymentCallBackRequest(
                transactionId = params.transactionId,
                orderId = params.orderId,
                amount = 3,
                currency = Currency.TWD
            )
        )
        return ResponseEntity.ok(ret);
    }

    @GetMapping("/jko")
    suspend fun jkoReturn(@ModelAttribute query: Any): ResponseEntity<String> {
        logger.info("Jko return: $query")
        return ResponseEntity.ok("Jko")
    }



    @GetMapping("/cancel")
    suspend fun cancel(): ResponseEntity<String> {
        return ResponseEntity.ok("Cancel")
    }
}
