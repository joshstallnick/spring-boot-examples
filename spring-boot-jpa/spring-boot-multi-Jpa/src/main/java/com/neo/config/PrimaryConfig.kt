package com.neo.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
    entityManagerFactoryRef = "entityManagerFactoryPrimary",
    transactionManagerRef = "transactionManagerPrimary",
    basePackages = ["com.neo.repository.test1"]
) //设置dao（repo）所在位置
open class PrimaryConfig(
    @Qualifier("primaryDataSource")
    private val primaryDataSource: DataSource,

    @Qualifier("vendorProperties")
    private val vendorProperties: Map<String, Any?>
) {
    @Bean(name = ["entityManagerFactoryPrimary"])
    @Primary
    open fun entityManagerFactoryPrimary(
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean =
        builder
            .dataSource(primaryDataSource)
            .properties(vendorProperties)
            .packages("com.neo.model") //设置实体类所在位置
            .persistenceUnit("primaryPersistenceUnit")
            .build()

    @Bean(name = ["entityManagerPrimary"])
    @Primary
    open fun entityManager(builder: EntityManagerFactoryBuilder): EntityManager =
        entityManagerFactoryPrimary(builder)
            .getObject()
            ?.createEntityManager()
            ?: throw Exception("Missing entity manager factory builder object")

    @Bean(name = ["transactionManagerPrimary"])
    @Primary
    open fun transactionManagerPrimary(builder: EntityManagerFactoryBuilder): PlatformTransactionManager =
        entityManagerFactoryPrimary(builder)
            .getObject()
            ?.let(::JpaTransactionManager)
            ?: throw Exception("Missing entity manager factory builder object")
}