package com.neo.repository

import org.junit.runner.RunWith
import com.neo.service.UserDetailService
import com.neo.param.UserDetailParam
import com.neo.model.UserDetail
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.Resource

@RunWith(SpringRunner::class)
@SpringBootTest
class JpaSpecificationTests {
    @Resource
    private lateinit var userDetailService: UserDetailService

    @Test
    fun testFindByCondition() {
        val page = 0
        val size = 10
        val sort = Sort(Sort.Direction.DESC, "id")
        val pageable: Pageable = PageRequest.of(page, size, sort)

        val param = UserDetailParam(
            introduction = "程序员",
            minAge = 10,
            maxAge = 30
        )

        userDetailService!!
            .findByCondition(param, pageable)
            .asSequence()
            .filterNotNull()
            .map(UserDetail::toString)
            .map("userDetail: "::plus)
            .forEach(::println)
    }
}