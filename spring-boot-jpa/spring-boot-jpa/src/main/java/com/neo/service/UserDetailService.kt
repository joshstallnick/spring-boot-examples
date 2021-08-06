package com.neo.service

import com.neo.param.UserDetailParam
import com.neo.model.UserDetail
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserDetailService {
    fun findByCondition(detailParam: UserDetailParam, pageable: Pageable): Page<UserDetail?>
}