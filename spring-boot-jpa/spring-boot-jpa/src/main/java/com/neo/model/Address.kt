package com.neo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Address(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var userId: Long? = null,
    var province: String? = null,
    var city: String? = null,
    var street: String? = null
)