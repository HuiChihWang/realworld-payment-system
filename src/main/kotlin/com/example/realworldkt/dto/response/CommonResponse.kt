package com.example.realworldkt.dto.response

data class CommonResponse(
    val status:String,
    val message: String,
    val data: Any? = null
)
