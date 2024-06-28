package com.example.realworldkt.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import java.time.Duration

@Configuration(proxyBeanMethods = false)
@EnableCaching
class CacheConfig {
    companion object {
        const val EXCHANGE_RATES_API_CACHE = "exchange_rates_api"
    }

    @Value("\${app.exchange-api.cache-ttl}")
    private val exchangeApiCacheTtl: Int = 10

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
        val jsonSerializer = RedisSerializationContext.SerializationPair.fromSerializer(
            GenericJackson2JsonRedisSerializer()
        )
        return RedisCacheManagerBuilderCustomizer { builder ->
            builder
                .withCacheConfiguration(
                    EXCHANGE_RATES_API_CACHE,
                    RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofSeconds(exchangeApiCacheTtl.toLong()))
                        .serializeValuesWith(jsonSerializer)
                )
        }
    }
}
