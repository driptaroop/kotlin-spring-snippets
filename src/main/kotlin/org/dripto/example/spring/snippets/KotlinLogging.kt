package org.dripto.example.spring.snippets

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

/*START SNIPPET*/
val map = mutableMapOf<KClass<*>, Logger>()
inline val <reified T> T.log: Logger
    get() = map.getOrPut(T::class) {
        LoggerFactory.getLogger(
                if (T::class.isCompanion) T::class.java.enclosingClass else T::class.java)
    }
/*END SNIPPET*/

/*EXAMPLE*/
@Configuration
class KotlinLogging {
    companion object {
        fun blah() {
            log.info("=============logging in companion============$log")
        }
    }

    @Bean
    fun loggingRunner() = ApplicationRunner {
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
