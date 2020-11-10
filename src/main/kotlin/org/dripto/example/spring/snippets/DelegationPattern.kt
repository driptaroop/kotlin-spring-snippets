package org.dripto.example.spring.snippets

import org.dripto.example.spring.snippets.Demeanor.CAUTIOUS
import org.dripto.example.spring.snippets.Demeanor.FRIENDLY
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

// INTERFACES
interface Soundable {
    fun sound(): String
}
interface Interactable {
    fun demeanor(): Demeanor
}
enum class Demeanor { FRIENDLY, CAUTIOUS }

interface Animal: Soundable, Interactable

//INDIVIDUAL IMPLs
@Component
class HowlImpl: Soundable {
    override fun sound(): String = "Howl!!"
}
@Component
class FriendlyDemeanorImpl: Interactable {
    override fun demeanor(): Demeanor = FRIENDLY
}
@Component
class CautiousDemeanorImpl: Interactable {
    override fun demeanor(): Demeanor = CAUTIOUS
}

//DELEGATION PATTERN
@Component("dog")
class DefaultDogImpl(howl: HowlImpl, friend: FriendlyDemeanorImpl)
    : Animal, Soundable by howl, Interactable by friend

@Component("wolf")
class DefaultWolfImpl(howl: HowlImpl, cautious: CautiousDemeanorImpl)
    : Animal, Soundable by howl, Interactable by cautious

//RUNNER
@Configuration
class DelegationPattern {
    @Bean
    fun delegationRunner(dog: Animal, wolf: Animal) = ApplicationRunner {
        log.info("${dog.sound()} ${dog.demeanor()}")
        log.info("${wolf.sound()} ${wolf.demeanor()}")
    }
}