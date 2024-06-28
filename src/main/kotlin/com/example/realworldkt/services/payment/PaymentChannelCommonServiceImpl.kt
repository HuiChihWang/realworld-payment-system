package com.example.realworldkt.services.payment

import com.example.realworldkt.dto.payment.ChannelQueryCondition
import com.example.realworldkt.dto.payment.PaymentChannel
import com.example.realworldkt.dto.payment.PaymentChannelInterface
import com.example.realworldkt.factories.payment.PaymentChannelServiceFactory
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelCommonService
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.URI

@Service
class PaymentChannelCommonServiceImpl(
    private val channelServiceFactory: PaymentChannelServiceFactory
) : IPaymentChannelCommonService {
    private val logger = LoggerFactory.getLogger(PaymentChannelCommonServiceImpl::class.java)

    private val channels = mapOf(
        "1234" to PaymentChannel(
            code = "LINE_PAY",
            paymentUrl = "http://localhost:8080/api/payment/channel/1234/pay",
            paymentMethod = "LINE_PAY",
            interfaceSetting = PaymentChannelInterface(
                name = "LINE PAY",
                logoUrl = URI.create("https://linepay.com/logo.png")
            )
        ),
        "5678" to PaymentChannel(
            code = "STRIPE",
            paymentUrl = "http://localhost:8080/api/payment/channel/5678/pay",
            paymentMethod = "CREDIT_CARD",
            interfaceSetting = PaymentChannelInterface(
                name = "STRIPE",
                logoUrl = URI.create("https://stripe.com/logo.png")
            )
        ),
        "9453" to PaymentChannel(
            code = "RANDOM",
            paymentUrl = "http://localhost:8080/api/payment/channel/9453/pay",
            paymentMethod = "RANDOM",
            interfaceSetting = PaymentChannelInterface(
                name = "RANDOM PAY",
                logoUrl = URI.create("https://randompay.com/logo.png")
            )
        ),

        "9487" to PaymentChannel(
            code = "JKO_PAY",
            paymentUrl = "http://localhost:8080/api/payment/channel/9487/pay",
            paymentMethod = "JKO_PAY",
            interfaceSetting = PaymentChannelInterface(
                name = "JKO PAY",
                logoUrl = URI.create("https://jkopay.com/logo.png")
            )
        )
    )

    override suspend fun queryAvailableChannels(condition: ChannelQueryCondition): List<PaymentChannel> {
        return channels.values.toList()
    }

    override suspend fun getChannel(channelId: Long): IPaymentChannelService {
        val channelSetting = channels[channelId.toString()] ?: throw IllegalArgumentException("Channel not found")
        logger.info("Channel setting: $channelSetting")
        return channelServiceFactory.getChannelService(channelSetting.code)

    }
}
