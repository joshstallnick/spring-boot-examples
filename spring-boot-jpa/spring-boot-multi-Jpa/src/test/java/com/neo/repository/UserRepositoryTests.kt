package com.neo.repository

import com.neo.model.User
import org.junit.runner.RunWith
import com.neo.repository.test1.UserTest1Repository
import com.neo.repository.test2.UserTest2Repository
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.lang.Exception
import java.text.DateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*
import javax.annotation.Resource
import kotlin.Throws

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTests {
    @Resource
    private lateinit var userTest1Repository: UserTest1Repository

    @Resource
    private lateinit var userTest2Repository: UserTest2Repository

    @Test
    @Throws(Exception::class)
    fun testSave() {
        val date = Instant.now()
        val dateString = date.toString()

        val users: List<User> = sequenceOf("aa", "bb", "cc")
            .map {
                User(
                    username = it,
                    nickname = it,
                    password = "${it}123456",
                    email = "$it@126.com",
                    regTime = dateString
                )
            }
            .toList()

        users.subList(0, 1).forEach(userTest1Repository::save)

        userTest2Repository.save(users.last())
    }

    @Test
    @Throws(Exception::class)
    fun testDelete() {
        userTest1Repository.deleteAll()
        userTest2Repository.deleteAll()
    }

    @Test
    fun testBaseQuery() {
        val date = Instant.now()
        val dateString = date.toString()

        val user = User(
            username = "ff",
            password = "ff123456",
            email = "ff@126.com",
            nickname = "ff",
            regTime = dateString
        )

        with(userTest1Repository) {
            findAll()
            findById(3L)
        }

        userTest2Repository.save(user)

        user.id = 2L

        with(userTest1Repository) {
            delete(user)
            count()
        }

        userTest2Repository.findById(3L)
    }
}