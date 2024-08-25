package self.study.sels

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SelsApplication

fun main(args: Array<String>) {
    runApplication<SelsApplication>(*args)
}
