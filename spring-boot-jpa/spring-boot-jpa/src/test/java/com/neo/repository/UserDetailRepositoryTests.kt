package com.neo.repository

import com.neo.model.Address
import com.neo.model.UserDetail
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest
class UserDetailRepositoryTests {
    @Resource
    private lateinit var addressRepository: AddressRepository

    @Resource
    private lateinit var userDetailRepository: UserDetailRepository

    @Test
    fun testSaveAddress() {
        Address(
            userId = 1L,
            city = "北京",
            province = "北京",
            street = "分钟寺"
        ).also(addressRepository::save)
    }

    @Test
    fun testSaveUserDetail() {
        UserDetail(
            userId = 3L,
            hobby = "吃鸡游戏",
            age = 28,
            introduction = "一个爱玩的人"
        ).also(userDetailRepository::save)
    }

    @Test
    fun testUserInfo() {
        userDetailRepository
            .findUserInfo("钓鱼")
            .asSequence()
            .map { "userInfo: ${it.username}-${it.email}-${it.hobby}-${it.introduction}" }
            .forEach(::println)
    }
}