package com.neo.repository

import com.neo.model.UserDetail
import com.neo.model.UserInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface UserDetailRepository : JpaSpecificationExecutor<UserDetail>, JpaRepository<UserDetail, Long> {
    fun findByHobby(hobby: String): UserDetail?

    @Query(
        "select u.username as userName, " +
                "u.email as email, " +
                "d.introduction as introduction, " +
                "d.hobby as hobby from User u, " +
                "UserDetail d " +
                "where u.id=d.userId " +
                "and d.hobby = ?1"
    )
    fun findUserInfo(hobby: String): List<UserInfo>
}