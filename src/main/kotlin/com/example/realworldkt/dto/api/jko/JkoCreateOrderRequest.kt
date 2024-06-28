package com.example.realworldkt.dto.api.jko

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder

data class JkoCreateOrderRequest(
    @JsonProperty("platform_order_id")
    val platformOrderId: String,

    @JsonProperty("store_id")
    val storeId: String,

    @JsonProperty("currency")
    val currency: String,

    @JsonProperty("total_price")
    val totalPrice: Number,

    @JsonProperty("final_price")
    val finalPrice: Number,

    @JsonProperty("result_url")
    val resultUrl: String,

    @JsonProperty("unredeem")
    val unRedeem: Number? = null,

    @JsonProperty("confirm_url")
    val confirmUrl: String? = null,

    @JsonProperty("result_display_url")
    val resultDisplayUrl: String? = null,
) {
    fun toJsonString(): String {
        return jacksonMapperBuilder().build().writeValueAsString(this)
    }
}
