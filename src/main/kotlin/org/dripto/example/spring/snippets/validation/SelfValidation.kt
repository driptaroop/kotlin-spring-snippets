package org.dripto.example.spring.snippets.validation

import org.dripto.example.spring.snippets.logging.log
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Validation
import javax.validation.ValidatorFactory
import javax.validation.constraints.Min
import javax.validation.constraints.Size

private val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()

fun <T> T.validate(additionalValidators: Set<(T) -> Unit> = setOf()) {
    val violations: Set<ConstraintViolation<T>> = factory.validator.validate(this)
    if (violations.isNotEmpty()) {
        throw ConstraintViolationException(violations)
    } else {
        additionalValidators.forEach { it(this) }
    }
}

val ageMaxValidator: (SelfValidatingPerson) -> Unit = {
    if (it.age > 150) throw IllegalArgumentException("age cannot be more than 150")
}

data class SelfValidatingPerson(
    val name: String,
    @field:Min(0)
    val age: Int
) {
    init { validate(additionalValidators = setOf(ageMaxValidator)) }
}

data class SelfValidatingAddress(
    val city: String,
    @field:Size(min = 5, max = 5)
    val pin: String
) {
    init { validate() }
}

@Component
class SelfValidationRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        println(SelfValidatingPerson("dripto", 31))
        try {
            println(SelfValidatingPerson("dripto", -31))
        } catch (e: ConstraintViolationException) {
            log.error(e.message)
        }
        try {
            println(SelfValidatingPerson("dripto", 231))
        } catch (e: IllegalArgumentException) {
            log.error(e.message)
        }
        try {
            println(SelfValidatingAddress("Berlin", "12169"))
            println(SelfValidatingAddress("Kolkata", "700047"))
        } catch (e: ConstraintViolationException) {
            log.error(e.message)
        }
    }
}
