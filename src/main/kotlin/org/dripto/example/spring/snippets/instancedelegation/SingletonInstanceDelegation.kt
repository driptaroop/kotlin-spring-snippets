package org.dripto.example.spring.snippets.instancedelegation

import org.dripto.example.spring.snippets.Application
import org.dripto.example.spring.snippets.logging.log
import org.springframework.beans.factory.getBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties(prefix = "say")
data class CustomDelegateProperties(
    val something: String
) {
    companion object : InstanceDelegator<CustomDelegateProperties> by typedDelegator()
}

interface InstanceDelegator<T> {
    val instance: T
    fun reset()
}

inline fun <reified T : Any> typedDelegator() = object : InstanceDelegator<T> {
    private var cached: T? = null
    override fun reset() { cached = null }
    override val instance: T
        get() = cached ?: Application.context.getBean<T>().also { cached = it }
}

@Component
class SingletonInstanceDelegation : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        log.info(CustomDelegateProperties.instance.something)
    }
}
