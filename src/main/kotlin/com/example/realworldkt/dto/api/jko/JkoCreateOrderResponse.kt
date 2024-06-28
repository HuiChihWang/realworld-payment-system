package com.example.realworldkt.dto.api.jko

import com.fasterxml.jackson.annotation.JsonProperty

data class JkoCreateOrderResponse(
    @JsonProperty("result")
    val result: String,

    @JsonProperty("message")
    val message: String?,

    @JsonProperty("result_object")
    val resultObject: ResultObject?
)

data class ResultObject(
    @JsonProperty("payment_url")
    val paymentUrl: String,

    @JsonProperty("qr_img")
    val qrImg: String,

    @JsonProperty("qr_timeout")
    val qrTimeout: Long
)
