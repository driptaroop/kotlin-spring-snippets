package org.dripto.example.spring.snippets.logs

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

fun main() {
    runApplication<KotlinLogging>()
}

@SpringBootApplication
class KotlinLogging {
    companion object {
        fun blah() {
            log.info("=============logging in companion============$log")
        }
    }

    @Bean
    fun runner() = ApplicationRunner {
        log.info("=============logging in app runner============$log")
        blah()
    }
}

@Component
class NewClass {
    @PostConstruct
    fun doThing() {
        log.info("=============logging in NewClass============${log.javaClass}")
    }
}
val map = mutableMapOf<KClass<*>, Logger>()
inline val <reified T> T.log: Logger
    get() = map.getOrPut(T::class) {
        LoggerFactory.getLogger(
                if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java)
    }

