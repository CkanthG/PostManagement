package com.home.post.config

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.validation.constraints.NotBlank

@Configuration
@ConfigurationProperties(value = "rabbitmq")
class RabbitMQConfig {
    @NotBlank
    var rabbitHost: String = ""
    var rabbitPort: Int = 5672
    @NotBlank
    var rabbitUser: String = ""
    @NotBlank
    var rabbitPassword: String = ""
    @NotBlank
    var postsQueue: String = ""

    companion object{
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
    @Bean
    fun provideChannel(connection: Connection): Channel {
        logger.debug("RabbitMQConfig:provideChannel execution started")
        return connection.createChannel()
    }

    @Bean
    fun provideConnection(connectionFactory: ConnectionFactory): Connection {
        logger.debug("RabbitMQConfig:provideConnection execution started")
        return connectionFactory.newConnection()
    }

    @Bean
    fun provideConnectionFactory(): ConnectionFactory {
        logger.debug("RabbitMQConfig:provideConnectionFactory execution started")
        val cf = ConnectionFactory()
        cf.host = rabbitHost
        cf.port = rabbitPort
        cf.username = rabbitUser
        cf.password = rabbitPassword
        cf.isAutomaticRecoveryEnabled = true
        cf.isTopologyRecoveryEnabled = true
        cf.networkRecoveryInterval = 1000
        return cf
    }
}