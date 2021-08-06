package com.neo.service

import com.neo.model.UserDetail
import com.neo.param.UserDetailParam
import com.neo.repository.UserDetailRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Service
class UserDetailServiceImpl(private val userDetailRepository: UserDetailRepository) : UserDetailService {
    override fun findByCondition(detailParam: UserDetailParam, pageable: Pageable): Page<UserDetail?> =
        userDetailRepository.findAll(
            { root: Root<UserDetail?>, query: CriteriaQuery<*>, cb: CriteriaBuilder ->
                buildQuery(detailParam, root, query, cb)
            }, pageable
        )

    private fun buildQuery(
        detailParam: UserDetailParam,
        root: Root<UserDetail?>,
        query: CriteriaQuery<*>,
        builder: CriteriaBuilder
    ): Predicate {
        val predicates: MutableList<Predicate> = ArrayList()

        //equal 示例
        detailParam
            .introduction
            .letNotBlank {
                root
                    .get<Any>("introduction")
                    .let { path -> builder.equal(path, it) }
                    .also(predicates::add)
            }

        //like 示例
        detailParam
            .realName
            .letNotBlank {
                root
                    .get<String>("realName")
                    .let { path -> builder.like(path, "%$it%") }
                    .also(predicates::add)
            }

        //between 示例
        listOf(detailParam.minAge, detailParam.maxAge).letNotNull {
            root
                .get<Int>("age")
                .let { path -> builder.between(path, it.first(), it[1]) }
                .also(predicates::add)
        }

        //greaterThan 大于等于示例
        listOf(detailParam.minAge).letNotNull {
            root
                .get<Int>("age")
                .let { path -> builder.greaterThan(path, it.first()) }
                .also(predicates::add)
        }

        return query.where(*predicates.toTypedArray()).restriction
    }
}

fun <R> String?.letNotBlank(fn: (String) -> R): R? =
    this?.let {
        if (it.isNotBlank()) return fn(it)
        null
    }

fun <R> List<Int?>.letNotNull(fn: (List<Int>) -> R): R? {
    if (this.all { it != null }) return fn(this.filterNotNull())
    return null
}