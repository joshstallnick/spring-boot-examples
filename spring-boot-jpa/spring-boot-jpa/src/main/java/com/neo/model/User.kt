package com.neo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class User(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false, unique = true)
    var email: String? = null,

    @Column(nullable = true, unique = true)
    var nickname: String? = null,

    @Column(nullable = false)
    var regTime: String? = null
)