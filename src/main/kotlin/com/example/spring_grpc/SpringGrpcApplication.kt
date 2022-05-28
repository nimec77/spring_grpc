package com.example.spring_grpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringGrpcApplication

fun main(args: Array<String>) {
    runApplication<SpringGrpcApplication>(*args)
}
