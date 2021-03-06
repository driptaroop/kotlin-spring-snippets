package org.dripto.example.spring.snippets.testcontainers

import org.dripto.example.spring.snippets.logging.log
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.testcontainers.containers.PostgreSQLContainer
import java.util.UUID
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.persistence.Entity
import javax.persistence.Id
import javax.sql.DataSource

/*START SNIPPET*/
@Component
@Profile("local")
class PostgresContainer(
    imageName: String = "postgres"
) : PostgreSQLContainer<PostgresContainer>(imageName) {
    @PostConstruct fun setup() = start()
    @PreDestroy fun teardown() = stop()
}
/*END SNIPPET*/

/*EXAMPLE*/
@Configuration
class TestcontainersSpring {
    @Bean
    @ConditionalOnBean(PostgresContainer::class)
    fun datasource(container: PostgresContainer): DataSource = DataSourceBuilder.create()
            .apply {
                with(container) {
                    driverClassName(driverClassName)
                    url(jdbcUrl)
                    username(username)
                    password(password)
                }
            }.build()

    @Bean
    fun testcontainersRunner(repository: StudentRepository) = ApplicationRunner {
        log.info(repository.save(StudentTC(name = "dripto", age = 12)).toString())
        log.info(repository.save(StudentTC(name = "tuki", age = 22)).toString())
        log.info(repository.findAll().toString())
    }
}

@Entity
data class StudentTC(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val age: Int
)

interface StudentRepository : JpaRepository<StudentTC, UUID>
