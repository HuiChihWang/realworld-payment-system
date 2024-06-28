package com.example.realworldkt.factories.payment

import com.example.realworldkt.services.payment.gateway.JkoPayGatewayService
import com.example.realworldkt.services.payment.gateway.LinePayGatewayService
import com.example.realworldkt.services.payment.gateway.StripeGatewayService
import com.example.realworldkt.services.payment.interfaces.IPaymentGatewayService
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component

@Component
class PaymentGatewayServiceFactory(
    private val applicationContext: ApplicationContext
) {
    fun getPaymentGatewayService(name: String): IPaymentGatewayService {
        return when (name) {
            "STRIPE" -> applicationContext.getBean(StripeGatewayService::class.java)
            "LINE_PAY" -> applicationContext.getBean(LinePayGatewayService::class.java)
            "JKO_PAY" -> applicationContext.getBean(JkoPayGatewayService::class.java)
            else -> throw IllegalArgumentException("Unsupported payment gateway: $name")
        }
    }

}
