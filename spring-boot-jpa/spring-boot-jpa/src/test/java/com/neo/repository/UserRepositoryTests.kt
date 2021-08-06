package com.neo.repository

import com.neo.model.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit4.SpringRunner
import java.time.Instant
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTests {
    @Resource
    private lateinit var userRepository: UserRepository

    @Test
    fun testSave() {
        val date = Instant.now()
        val dateString = date.toString()

        sequenceOf("aa", "bb", "cc")
            .map {
                User(
                    username = it,
                    password = "${it}123456",
                    email = "$it@126.com",
                    nickname = it,
                    regTime = dateString
                )
            }
            .forEach(userRepository::save)

//		Assert.assertEquals(3, userRepository.findAll().size());
//		Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "bb@126.com").getNickName());
//		userRepository.delete(userRepository.findByUserName("aa"));
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

        with(userRepository) {
            findAll()
            findById(3)
            save(user)
            user.id = 2
            delete(user)
            count()
            existsById(3)
        }
    }

    @Test
    fun testCustomSql() {
        with(userRepository) {
            modifyById("neo", 3)
            deleteById(3)
            findByEmail("ff@126.com")
        }
    }

    @Test
    fun testPageQuery() {
        Sort(Sort.Direction.DESC, "id")
            .let { PageRequest.of(1, 2, it) }
            .also(userRepository::findAll)
            .also { userRepository.findByNickName("aa", it) }
    }
}