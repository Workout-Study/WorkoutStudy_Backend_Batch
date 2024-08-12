package com.fitmate.batchservice.persistence.repository

import com.fitmate.batchservice.persistence.entity.FitMateForRead
import org.springframework.data.jpa.repository.JpaRepository

interface FitMateForReadRepository : JpaRepository<FitMateForRead, Long> {
    fun findByFitGroupIdAndState(fitGroupId: Long, state: Boolean): List<FitMateForRead>
    fun findByFitMateUserIdAndState(userId: Int, state: Boolean): List<FitMateForRead>
}