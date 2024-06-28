package com.example.realworldkt.dto.api.linepay.response

data class LinePayApiResponse<T> (
    val returnCode: String = "",
    val returnMessage: String = "",
    val info: T? = null
)
