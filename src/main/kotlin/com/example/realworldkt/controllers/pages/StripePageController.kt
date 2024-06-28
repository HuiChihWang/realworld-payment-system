package com.example.realworldkt.controllers.pages

import com.example.realworldkt.config.payment.StripeApiConfig
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StripePageController(
    private val stripeApiConfig: StripeApiConfig
) {
    @GetMapping("hello")
    fun hello(model: Model): String {
        model.addAttribute("name", "World")
        return "greeting"
    }

    @GetMapping("/stripe")
    fun stripeTestPage(model: Model): String {
        model.addAttribute("stripePublicKey", stripeApiConfig.publicKey)
        return "stripe"
    }

}
