package com.tw.dbserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DbServerApplication

fun main(args: Array<String>) {
	runApplication<DbServerApplication>(*args)
}
