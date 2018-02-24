package ru.zahv.alex.parser

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ParserApplication

fun main(args: Array<String>) {
    SpringApplication.run(ParserApplication::class.java, *args)
}
