package com.example.realworldkt.services.api

import com.example.realworldkt.config.payment.StripeApiConfig
import com.example.realworldkt.services.order.OrderEntity
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import com.stripe.model.checkout.Session
import com.stripe.param.PaymentIntentCreateParams
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.stereotype.Service

@Service
class StripeApiService(
    private val stripeApiConfig: StripeApiConfig
) {
    init {
        Stripe.apiKey = stripeApiConfig.secretKey
    }

    fun createPaymentIntent(amount: Number, currency: String): PaymentIntent {
        var convertedAmount = amount.toDouble()
        if (currency == "USD") {
            convertedAmount *= 100
        }

        val paymentIntentParams = PaymentIntentCreateParams.builder()
            .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
            .setCurrency(currency.lowercase())
            .setAmount(convertedAmount.toLong())
            .build()
        return PaymentIntent.create(paymentIntentParams)
    }

    //TODO: implement
    fun capturePaymentIntent(paymentIntentId: String): PaymentIntent {
        val paymentIntent = PaymentIntent.retrieve(paymentIntentId)
        return paymentIntent.capture()
    }

    suspend fun createCheckoutSession(order: OrderEntity): Session {
        val items = order.orderProducts.map { productOrder ->
            val productDetail = productOrder.product
            val productPrice = productOrder.product.productPrice.amount.toLong() * 100
            SessionCreateParams.LineItem.builder()
                .setQuantity(productOrder.quantity.toLong())
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productOrder.totalPrice.currency.lowercase())
                        .setUnitAmount(productPrice)
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(productDetail.productName)
                                .setDescription(productDetail.productDescription)
                                .build()
                        )
                        .build()
                )
                .build()
        }

        val checkoutSessionParams = SessionCreateParams.builder()
            .addAllPaymentMethodType(listOf(
                SessionCreateParams.PaymentMethodType.CARD,
                SessionCreateParams.PaymentMethodType.AMAZON_PAY,
            ))
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setCustomerEmail("taya30621@gmail.com")
            .addAllLineItem(items)
            .setSuccessUrl("http://localhost:8080/api/stripe/success")
            .setCancelUrl("http://localhost:8080/api/stripe/cancel")
            .build()

        return Session.create(checkoutSessionParams)
    }
}
