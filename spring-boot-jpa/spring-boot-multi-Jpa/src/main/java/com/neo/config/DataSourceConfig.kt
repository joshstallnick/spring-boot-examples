package com.neo.config

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
open class DataSourceConfig(
    private val jpaProperties: JpaProperties,
    private val hibernateProperties: HibernateProperties
) {

    @Bean(name = ["primaryDataSource"])
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    open fun firstDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["secondaryDataSource"])
    @ConfigurationProperties("spring.datasource.secondary")
    open fun secondDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean(name = ["vendorProperties"])
    open fun getVendorProperties(): Map<String, Any> =
        hibernateProperties.determineHibernateProperties(jpaProperties.properties, HibernateSettings())
}