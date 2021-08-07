package com.neo.model

import java.io.Serializable
import javax.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0,

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
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}