package com.example.realworldkt.services.payment.interfaces

import com.example.realworldkt.dto.payment.ChannelQueryCondition
import com.example.realworldkt.dto.payment.PaymentChannel

interface IPaymentChannelCommonService {
    suspend fun queryAvailableChannels(condition: ChannelQueryCondition): List<PaymentChannel>
    suspend fun getChannel(channelId: Long): IPaymentChannelService
}
