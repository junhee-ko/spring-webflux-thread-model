package jko.springwebfluxthreadmodel

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

private val logger = LoggerFactory.getLogger(Application::class.java)

fun main(args: Array<String>) {
    runApplication<Application>(*args)

    logger.info("CPU: ${Runtime.getRuntime().availableProcessors()}")
}
