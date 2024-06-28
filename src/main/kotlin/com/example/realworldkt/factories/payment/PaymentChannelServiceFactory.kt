package com.example.realworldkt.factories.payment

import com.example.realworldkt.services.payment.channels.PaymentChannelService
import com.example.realworldkt.services.payment.channels.RandomChannelService
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelService
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component

@Component
class PaymentChannelServiceFactory(
    private val appContext: ApplicationContext,
    private val gatewayServiceFactory: PaymentGatewayServiceFactory
) {
    fun getChannelService(type: String): IPaymentChannelService {
        return when (type) {
            "RANDOM" -> appContext.getBean(RandomChannelService::class.java)
            else -> PaymentChannelService(gatewayServiceFactory.getPaymentGatewayService(type))
        }
    }
}
