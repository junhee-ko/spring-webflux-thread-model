package jko.springwebfluxthreadmodel

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux

@RestController
@RequestMapping
class NumbersController(
    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/local")
    fun getNumbers(): Flux<Int> {
        logger.info("-- getNumbers")

        return Flux.fromIterable(listOf(1, 2, 3))
    }

    @GetMapping("/remote")
    fun getRemoteNumbers(): Flux<Int> {
        return Flux.fromIterable(listOf(4, 5, 6))
    }

    @GetMapping("/local/remote")
    fun getNumbersMergedWithRemote(): Flux<Int> {
        logger.info("-- Start getNumbersMergedWithRemote")

        val numbers: Flux<Int> = Flux.fromIterable(listOf(1, 2, 3))
        val numbersFromRemote: Flux<Int> = webClient.get()
            .uri("/remote")
            .retrieve()
            .bodyToFlux<Int>()
            .log()

        return numbers.concatWith(numbersFromRemote)
    }
}
