package com.neo.repository.test1

import com.neo.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserTest1Repository : JpaRepository<User, Long> {
    override fun findById(id: Long): Optional<User>
    fun findByUserName(username: String): User?
    fun findByUserNameOrEmail(username: String, email: String): User?
}