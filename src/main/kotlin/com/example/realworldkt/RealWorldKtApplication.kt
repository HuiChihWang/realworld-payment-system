package com.example.realworldkt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class RealWorldKtApplication

fun main(args: Array<String>) {
    runApplication<RealWorldKtApplication>(*args)
}
