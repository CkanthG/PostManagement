package com.home.post

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class PostManagementApplication

fun main(args: Array<String>) {
    runApplication<PostManagementApplication>(*args)
}
