package com.neo.repository

import com.neo.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByUserName(username: String): User?
    fun findByUserNameOrEmail(username: String, email: String): User?

    @Transactional(timeout = 10)
    @Modifying
    @Query("update User set username = ?1 where id = ?2")
    fun modifyById(username: String, id: Long): Int

    @Transactional
    @Modifying
    @Query("delete from User where id = ?1")
    fun deleteById(id: Long)

    @Query("select u from User u where u.email = ?1")
    fun findByEmail(email: String): User?

    @Query("select u from User u")
    fun findALL(pageable: Pageable?): Page<User?>
    fun findByNickName(nickname: String, pageable: Pageable): Page<User?>
    fun findByNickNameAndEmail(nickname: String, email: String, pageable: Pageable): Slice<User?>
}