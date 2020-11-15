package org.dripto.example.spring.snippets

import java.util.UUID
import kotlin.properties.Delegates

class PersonDSL {
    lateinit var id: UUID
    lateinit var name: String
    var age by Delegates.notNull<Int>()
}

fun main() {
    val person: PersonDSL = personDSL {
        id = UUID.randomUUID()
        name = ""
        age = 1
    }

    println(person)
}

fun personDSL(block: PersonDSL.() -> Unit): PersonDSL = PersonDSL().apply(block)


