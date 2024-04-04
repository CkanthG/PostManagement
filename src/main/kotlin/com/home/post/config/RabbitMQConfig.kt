package com.home.post.config

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
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

    @Bean
    fun provideChannel(connection: Connection): Channel {
        return connection.createChannel()
    }

    @Bean
    fun provideConnection(connectionFactory: ConnectionFactory): Connection {
        return connectionFactory.newConnection()
    }

    @Bean
    fun provideConnectionFactory(): ConnectionFactory {
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