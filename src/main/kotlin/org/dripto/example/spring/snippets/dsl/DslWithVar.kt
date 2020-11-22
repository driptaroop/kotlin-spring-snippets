package org.dripto.example.spring.snippets.dsl

class DslWithVar {
    // code for dsl
    fun person(block: Person.() -> Unit) = Person().apply(block)
    fun Person.address(block: Address.() -> Unit) {
        address = Address().apply(block)
    }

    // models
    data class Person(
        var name: String? = null,
        var age: Int? = null,
        var address: Address? = null
    )

    data class Address(
        var street: String? = null,
        var number: Int? = null,
        var city: String? = null
    )

    // usage
    fun initialize() {
        val p = person {
            name = "dripto"
            age = 31
            address {
                street = "berlin"
                number = 100
                city = "berlin"
            }
        }
    }
}

fun main() {
    DslWithVar().initialize()
}
