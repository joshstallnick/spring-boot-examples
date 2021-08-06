package com.neo.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserDetail(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var userId: Long? = null,
    var age: Int? = null,
    var realName: String? = null,
    var status: String? = null,
    var hobby: String? = null,
    var introduction: String? = null,
    var lastLoginIp: String? = null
)