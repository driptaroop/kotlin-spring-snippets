package org.dripto.example.spring.snippets

import org.dripto.example.spring.snippets.instancedelegation.CustomDelegateProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

@SpringBootApplication
@EnableConfigurationProperties(CustomDelegateProperties::class)
class Application : ApplicationContextAware {
    companion object {
        lateinit var context: ApplicationContext
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}

fun main() {
    runApplication<Application>()
}
