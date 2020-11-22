package org.dripto.example.spring.snippets.dsl

import java.util.Date

class DslWithVal {
    // code for dsl
    @DslMarker
    annotation class PersonDsl
    fun person(block: PersonBuilder.() -> Unit) = PersonBuilder().apply(block).build()

    @PersonDsl
    class PersonBuilder {
        var name: String = ""
        var dateOfBirth: Date = Date()
        private val addresses = mutableListOf<Address>()
        fun address(block: AddressBuilder.() -> Unit) {
            addresses.add(AddressBuilder().apply(block).build())
        }
        fun build(): Person =
                Person(name, dateOfBirth, addresses.toList())
    }

    @PersonDsl
    class AddressBuilder {
        var street: String = ""
        var number: Int = 0
        var city: String = ""

        fun build() = Address(street, number, city)
    }

    // models
    data class Person(
        val name: String,
        val dateOfBirth: Date,
        var address: List<Address>
    )

    data class Address(
        val street: String,
        val number: Int,
        val city: String
    )

    // usage
    fun initialize() {
        val p: Person = person {
            name = "dripto"
            dateOfBirth = Date()
            address {
                street = "berlin"
                number = 100
                city = "berlin"
            }
            address {
                street = "kolkata"
                number = 200
                city = "kolkata"
            }
        }
        println(p)
    }
}

fun main() {
    DslWithVal().initialize()
}
