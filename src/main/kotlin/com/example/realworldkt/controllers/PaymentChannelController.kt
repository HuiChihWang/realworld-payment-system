package com.example.realworldkt.controllers

import com.example.realworldkt.dto.payment.ChannelQueryCondition
import com.example.realworldkt.dto.payment.PaymentChannel
import com.example.realworldkt.dto.payment.PaymentResult
import com.example.realworldkt.dto.request.PaymentRequest
import com.example.realworldkt.services.payment.interfaces.IPaymentChannelCommonService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payment/channel")
class PaymentChannelController(
    private val paymentChannelCommonService: IPaymentChannelCommonService,
) {
    @GetMapping
    suspend fun queryAvailableChannels(@RequestBody condition: ChannelQueryCondition?): ResponseEntity<List<PaymentChannel>> {
        val channels = paymentChannelCommonService.queryAvailableChannels(condition ?: ChannelQueryCondition())
        return ResponseEntity.ok(channels)
    }

    @PostMapping("/{channelId}/pay")
    suspend fun pay(@PathVariable("channelId") channelId: Long, @Valid @RequestBody request: PaymentRequest): ResponseEntity<PaymentResult> {
        val paymentChannelService = paymentChannelCommonService.getChannel(channelId)
        val ret = paymentChannelService.pay(request)
        return ResponseEntity.ok(ret)
    }
}
