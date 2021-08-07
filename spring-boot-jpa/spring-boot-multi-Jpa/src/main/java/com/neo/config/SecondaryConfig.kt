package com.neo.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManager
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactorySecondary",
    transactionManagerRef = "transactionManagerSecondary",
    basePackages = ["com.neo.repository.test2"]
)
open class SecondaryConfig(
    @Qualifier("secondaryDataSource")
    private val secondaryDataSource: DataSource,

    @Qualifier("vendorProperties")
    private val vendorProperties: Map<String, Any?>
) {
    @Bean(name = ["entityManagerFactorySecondary"])
    open fun entityManagerFactorySecondary(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean =
        builder
            .dataSource(secondaryDataSource)
            .properties(vendorProperties)
            .packages("com.neo.model")
            .persistenceUnit("secondaryPersistenceUnit")
            .build()

    @Bean(name = ["entityManagerSecondary"])
    open fun entityManager(builder: EntityManagerFactoryBuilder): EntityManager =
        entityManagerFactorySecondary(builder)
            .getObject()
            ?.createEntityManager()
            ?: throw Exception("Missing entity manager factory builder object")

    @Bean(name = ["transactionManagerSecondary"])
    open fun transactionManagerSecondary(builder: EntityManagerFactoryBuilder): PlatformTransactionManager =
        entityManagerFactorySecondary(builder)
            .getObject()
            ?.let(::JpaTransactionManager)
            ?: throw Exception("Missing entity manager factory builder object")
}